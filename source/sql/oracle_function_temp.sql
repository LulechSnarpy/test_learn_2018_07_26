--创建包， 声明函数和参数类型
CREATE OR REPLACE PACKAGE ASSET_CHANGE
AS
	TYPE DATA_RECORD IS RECORD( --自定义数据类型-行（含字段及类型）
		--填入数据 类似 表创建
	);

	TYPE DATA_TABLE IS TABLE OF DATA_RECORD:--自定义表类型

	--声明函数 和 参数 入口函数
	FUNCTION GET_BALANCE (SERIALNUMBER VARCHAR2,ASSETYPE VARCHAR2,STARTCOUPONDATE VARCHAR2, ENDDAYCURRENT VARCHAR,BUZTYPE VARCHAR2,CUSNUMBER VARCHAR2)
		RETURN DATA_TABLE --返回表类型
		PIPELINED; --管道式
	--获取绝大部分的产品数据
	FUNCTION GET_ALMOST_BALANCE (SERIALNUMBER VARCHAR2,ASSETYPE VARCHAR2,STARTCOUPONDATE VARCHAR2, ENDDAYCURRENT VARCHAR,BUZTYPE VARCHAR2,CUSNUMBER VARCHAR2)
		RETURN DATA_RECORD;--返回单数据类型
END ASSET_CHANGE


SELECT ASSET_CHANGE.GET_BALANCE() FROM DUAL; --包外调用 需要包名.函数名


--实现包中定义的函数
CREATE OR REPLACE BODY ASSET_CHANGE AS
	FUNCTION GET_BALANCE (SERIALNUMBERS VARCHAR2,ASSETYPES VARCHAR2,STARTCOUPONDATE VARCHAR2, ENDDAYCURRENT VARCHAR,BUZTYPE VARCHAR2,CUSNUMBER VARCHAR2)
		RETURN DATA_TABLE --返回表类型
		PIPELINED
	DECLARE
		v_list VARCHAR2(4000);
		v_list1 VARCHAR2(4000);
		l_idx PLS_INTEGER;
		l_idx1 PLS_INTEGER;
		l_idx2 PLS_INTEGER;
		p_sep VARCHAR2(1) := ',';
		assetype VARCHAR2(200);
		serialnum varchar2(200);
		TYPE STR_A IS VARRAY(14) OF VARCHAR2(15);
		assetype_a STR_A := STR_A('','','','','','','','','','','','','','');
		TYPE BCT  IS REF CURSOR;
		v_bc BCT;
		v_stmt_str VARCHAR2(200);
		B_RECORD DATA_RECORD;
	BEGIN
		v_list := ASSETYPES;
		v_list1 := SERIALNUMBERS;
		l_idx2 := 0;
		LOOP
			l_idx2 := l_idx2 + 1;
			l_idx := "INSTR"(v_list, p_sep);
					IF l_idx >0 THEN 
						assetype := "SUBSTR"(v_list, 1, l_idx-1);
						v_list := "SUBSTR"(v_list, l_idx+"LENGTH"(p_sep));
						assetype_a(l_idx2):= assetype;
					ELSE
						assetype:= v_list;
						assetype_a(l_idx2):= assetype;
						EXIT;
					END IF;
		END LOOP;
		LOOP 
				l_idx1:= "INSTR"(v_list1, p_sep);
				IF l_idx1 >0 THEN
					serialnum := "SUBSTR"(v_list1, 1, l_idx1-1);
					v_list1 := "SUBSTR"(v_list1, l_idx1+"LENGTH"(p_sep));
					IF ASSETYPES IS NULL THEN
						l_idx2 := 1;
						v_stmt_str := 'select DISTINCT ASSETTYPE from BALANCE where KEEPFOLDER_ID = :j';
						OPEN v_bc FOR v_stmt_str USING serialnum;
							LOOP
								FETCH v_bc INTO assetype_a(l_idx2);
								EXIT WHEN v_bc%NOTFOUND;
								l_idx2 := l_idx2+1;	
							END LOOP;
						CLOSE v_bc;
						l_idx2 := l_idx2-1;
					END IF;
					FOR i in 1..l_idx2 LOOP
						B_RECORD := GET_ALMOST_BALANCE (serialnum ,ASSETYPE ,STARTCOUPONDATE , ENDDAYCURRENT ,BUZTYPE ,CUSNUMBER);
						PIPE ROW(B_RECORD);
					END LOOP;
				ELSE
					serialnum:= v_list1;
					IF ASSETYPES IS NULL THEN
						l_idx2 := 1;
						v_stmt_str := 'select DISTINCT ASSETTYPE from BALANCE where KEEPFOLDER_ID = :j';
						OPEN v_bc FOR v_stmt_str USING serialnum;
							LOOP
								FETCH v_bc INTO assetype_a(l_idx2);
								EXIT WHEN v_bc%NOTFOUND;
								l_idx2 := l_idx2+1;	
							END LOOP;
						CLOSE v_bc;
						l_idx2 := l_idx2-1;
					END IF;
					FOR i in 1..l_idx2 LOOP
						B_RECORD := GET_ALMOST_BALANCE (serialnum ,ASSETYPE ,STARTCOUPONDATE , ENDDAYCURRENT ,BUZTYPE ,CUSNUMBER);
						PIPE ROW(B_RECORD);
					END LOOP;
					EXIT;
				END IF;
		END LOOP;
	END GET_BALANCE;
	FUNCTION GET_ALMOST_BALANCE (SERIALNUMBER VARCHAR2,ASSETYPE VARCHAR2,STARTCOUPONDATE VARCHAR2, ENDDAYCURRENT VARCHAR,BUZTYPE VARCHAR2,CUSNUMBER VARCHAR2)
		RETURN DATA_RECORD
	IS
		CONDITIONS VARCHAR2(4000);
		CONDITIONS_ONE VARCHAR2(4000);
		CONDITIONS_TWO VARCHAR2(4000);
		SQL_T VARCHAR2(10000);
		SQL_NL VARCHAR2(40) := 'NVL((SELECT A.';
		SQL_FR VARCHAR2(40) := ' FROM (SELECT SETTLEDATE,';
		SQL_GB VARCHAR2(40) := ' GROUP BY SETTLEDATE,';
		SQL_OB VARCHAR2(40) := ' ORDER BY SETTLEDATE DESC,';
		SQL_AW VARCHAR2(40) := ' DESC) A WHERE ROWNUM = 1),0)';
		type S_ARRAY is varray(10) of varchar2(20);
		FIELDS S_ARRAY;
		FIELDS_2 S_ARRAY;
		B_RECORD DATA_RECORD;
	BEGIN
		CONDITIONS := 'FROM BALANCE WHERE CUSNUMBER = ' || CUSNUMBER; --拼接条件
		CONDITIONS := CONDITIONS || ' AND SERIALNUMBER = ' || SERIALNUMBER || ' AND ASSETYPE = ' || ASSETYPE;
		IF BUZTYPE IS NOT NULL THEN
		  CONDITIONS := CONDITIONS || ' AND BUZTYPE = ' || BUZTYPE;
		END IF
		CONDITIONS_ONE := CONDITIONS|| 'AND SETTLEDATE >= ' || STARTCOUPONDATE 'AND SETTLEDATE <= ' || ENDDAYCURRENT; --日期条件1
		CONDITIONS_TWO := CONDITIONS|| 'AND SETTLEDATE <  ' || STARTCOUPONDATE;	--日期条件2
		--语句拼接
		FIELDS := S_ARRAY('INTERESTEARNING','RESERVEVALUE7','AMORTIZEEARNING','PRICEEARNING','FAIRVALUEINCOME','IMPAIRMENTLOST','RESERVEVALUE5','RESERVEVALUE6');
		FIELDS_2 := S_ARRAY('HOLDPOSITION','HOLDFACEAMOUNT','INTERESTADJUST','INTERESTCOST','FAIRVALUEALTER','IMPAIRMENT','CLEANPRICECOST','DIRTYPRICECOST');
		SQL_T := 'SELECT ';
		FOR i IN FIELDS.first..FIELDS.last LOOP
			SQL_T:= SQL_T || SQL_NL || FIELDS(i) || SQL_FR || FIELDS(i) || CONDITIONS_ONE
								|| SQL_GB || FIELDS(i) || SQL_OB || ' - ';
			SQL_T:=	SQL_T || SQL_NL || FIELDS(i) || SQL_FR || FIELDS(i) || CONDITIONS_TWO
								|| SQL_GB || FIELDS(i) || SQL_OB || 'AS ' || FIELDS(i) || ', ';
		END LOOP;
		FOR i IN FIELDS_2.first..FIELDS_2.last LOOP
			SQL_T:=	SQL_T || SQL_NL || FIELDS_2(i) || SQL_FR || FIELDS_2(i) || CONDITIONS_ONE
								|| SQL_GB || FIELDS_2(i) || SQL_OB || 'AS ' || FIELDS_2(i);
			IF i < FIELDS.last THEN 
					SQL_T := SQL_T || ', ';
			END IF;
		END LOOP;
		SQL_T := SQL_T || ' FROM DUAL';
		--执行语句
		EXECUTE IMMEDIATE SQL_T INTO 
			B_RECORD.INTERESTEARNING,B_RECORD.RESERVEVALUE7,B_RECORD.AMORTIZEEARNING,B_RECORD.PRICEEARNING,B_RECORD.FAIRVALUEINCOME
			,B_RECORD.IMPAIRMENTLOST,B_RECORD.RESERVEVALUE5,B_RECORD.RESERVEVALUE6,B_RECORD.HOLDPOSITION,B_RECORD.HOLDFACEAMOUNT
			,B_RECORD.INTERESTADJUST,B_RECORD.INTERESTCOST,B_RECORD.FAIRVALUEALTER,B_RECORD.IMPAIRMENT,B_RECORD.CLEANPRICECOST,B_RECORD.DIRTYPRICECOST;
		RETURN B_RECORD;
	END GET_ALMOST_BALANCE;
END ASSET_CHANGE;
	

--拼接条件
CONDITIONS := 'FROM BALANCE WHERE CUSNUMBER = ' || CUSNUMBER;
IF IS NOT NULL THEN
  CONDITIONS := CONDITIONS || ;
END IF
IF IS NOT NULL THEN
  CONDITIONS := CONDITIONS || ;
END IF
IF IS NOT NULL THEN
  CONDITIONS := CONDITIONS || ;
END IF

--日期条件1
CONDITIONS_ONE := CONDITIONS|| 'AND SETTLEDATE >= ' || STARTCOUPONDATE 'AND SETTLEDATE <= ' || ENDDAYCURRENT; 
--日期条件2
CONDITIONS_TWO := CONDITIONS|| 'AND SETTLEDATE <  ' || STARTCOUPONDATE;

--执行SQL
EXECUTE IMMEDIATE SQL_T INTO DATA_RECORD;


--创建函数
CREATE [OR REPLACE] FUNCTION function_name
 (arg1 [ { IN | OUT | IN OUT }] type1 [DEFAULT value1],
 [arg2 [ { IN | OUT | IN OUT }] type2 [DEFAULT value1]],
 ......
 [argn [ { IN | OUT | IN OUT }] typen [DEFAULT valuen]])
 [ AUTHID DEFINER | CURRENT_USER ]
RETURN return_type
 IS | AS
    <类型.变量的声明部分>
BEGIN
    执行部分
    RETURN expression
EXCEPTION
    异常处理部分
END function_name; 　　

--创建过程
CREATE [OR REPLACE] PROCEDURE procedure_name
([arg1 [ IN | OUT | IN OUT ]] type1 [DEFAULT value1],
 [arg2 [ IN | OUT | IN OUT ]] type2 [DEFAULT value1]],
 ......
 [argn [ IN | OUT | IN OUT ]] typen [DEFAULT valuen])
    [ AUTHID DEFINER | CURRENT_USER ]
{ IS | AS }
  <声明部分>
BEGIN
  <执行部分>
EXCEPTION
  <可选的异常错误处理程序>
END procedure_name;

--执行SQL 函数参数不能直接作为 SQL 语句的条件 需要进行语句拼接再执行
 EXECUTE IMMEDIATE (SQL_STR) [INTO (ROW)] USING [(ARG)];

 --样例
 1 /*** DDL ***/
 2 begin
 3     EXECUTE IMMEDIATE 'drop table temp_1';
 4     EXECUTE IMMEDIATE 'create table temp_1(name varchar2(8))';
 5 end;
 6 
 7 /*** DML ***/
 8 declare
 9     v_1 varchar2(8);
10     v_2 varchar2(10);
11     str varchar2(50);
12 begin
13     v_1:='测试人员';
14     v_2:='北京';
15     str := 'INSERT INTO test (name ,address) VALUES (:1, :2)';
16     EXECUTE IMMEDIATE str USING v_1, v_2;
17     commit;
18 end;

--动态语句 cursor 样例
DECLARE
	TYPE EmpCurTyp IS REF CURSOR;
	DATA_RECORD (tableName)%ROWTYPE;
  v_cursor EmpCurTyp;
BEGIN
  OPEN v_cursor FOR 'SELECT * FROM (tableName)';
	LOOP 
		FETCH v_cursor INTO DATA_RECORD;
		dbms_output.put_line(DATA_RECORD.(tablefield)));
		EXIT WHEN v_cursor%NOTFOUND;
	END LOOP;
	CLOSE v_cursor;
END;

--SQL拼接测试
DECLARE
	CONDITIONS VARCHAR2(200);
	CONDITIONS_ONE VARCHAR2(1000);
	CONDITIONS_TWO VARCHAR2(1000);
	SQL_T VARCHAR2(10000);
	SQL_NL VARCHAR2(40) := 'NVL((SELECT A.';
	SQL_FR VARCHAR2(40) := ' FROM (SELECT SETTLEDATE,';
	SQL_GB VARCHAR2(40) := ' GROUP BY SETTLEDATE,';
	SQL_OB VARCHAR2(40) := ' ORDER BY SETTLEDATE DESC,';
	SQL_AW VARCHAR2(40) := ' DESC) A WHERE ROWNUM = 1),0)';
	type S_ARRAY is varray(10) of varchar2(20);
	FIELDS S_ARRAY;
	FIELDS_2 S_ARRAY;
BEGIN
	FIELDS := S_ARRAY('INTERESTEARNING','RESERVEVALUE7','AMORTIZEEARNING','PRICEEARNING','FAIRVALUEINCOME','IMPAIRMENTLOST','RESERVEVALUE5','RESERVEVALUE6');
	FIELDS_2 := S_ARRAY('HOLDPOSITION','HOLDFACEAMOUNT','INTERESTADJUST','INTERESTCOST','FAIRVALUEALTER','IMPAIRMENT','CLEANPRICECOST','DIRTYPRICECOST');
	CONDITIONS := ' FROM BALANCE WHERE CUSTOMER_NUMBER = ' || '88001'; --拼接条件
	CONDITIONS_ONE := CONDITIONS|| ' AND SETTLEDATE >= ' || '20190403' || ' AND SETTLEDATE <= ' || '20190408'; --日期条件1
	CONDITIONS_TWO := CONDITIONS|| ' AND SETTLEDATE <  ' || '20190403' ;	--日期条件2
	--语句拼接
	SQL_T := 'SELECT ';
	FOR i IN FIELDS.first..FIELDS.last LOOP
		SQL_T:= SQL_T || SQL_NL || FIELDS(i) || SQL_FR || FIELDS(i) || CONDITIONS_ONE
							|| SQL_GB || FIELDS(i) || SQL_OB || FIELDS(i) || SQL_AW || ' - ';
		SQL_T:=	SQL_T || SQL_NL || FIELDS(i) || SQL_FR || FIELDS(i) || CONDITIONS_TWO
							|| SQL_GB || FIELDS(i) || SQL_OB || FIELDS(i) || SQL_AW || 'AS ' || FIELDS(i) || ', ';
	END LOOP;
	FOR i IN FIELDS_2.first..FIELDS_2.last LOOP
		SQL_T:=	SQL_T || SQL_NL || FIELDS_2(i) || SQL_FR || FIELDS_2(i) || CONDITIONS_ONE
							|| SQL_GB || FIELDS_2(i) || SQL_OB || FIELDS_2(i) || SQL_AW || 'AS ' || FIELDS_2(i);
		IF i < FIELDS.last THEN 
				SQL_T := SQL_T || ', ';
		END IF;
	END LOOP;
	SQL_T := SQL_T || ' FROM DUAL';
	dbms_output.put_line(SQL_T);
	EXECUTE IMMEDIATE SQL_T;
END;

--数组保存
DECLARE
	v_list VARCHAR2(4000);
	v_list1 VARCHAR2(4000);
	l_idx PLS_INTEGER;
	l_idx1 PLS_INTEGER;
	l_idx2 PLS_INTEGER;
	p_sep VARCHAR2(1) := ',';
	assetype VARCHAR2(200);
	serialnum varchar2(200);
	TYPE STR_A IS VARRAY(14) OF VARCHAR2(15);
	assetype_a STR_A := STR_A('','','','','','','','','','','','','','');
	TYPE BCT  IS REF CURSOR;
	v_bc BCT;
	v_stmt_str VARCHAR2(200);
BEGIN
	v_list := '203101,1,234123';
	v_list1 := '203101,203102';
	l_idx2 := 0;
	LOOP
		l_idx2 := l_idx2 + 1;
		l_idx := "INSTR"(v_list, p_sep);
				IF l_idx >0 THEN 
					assetype := "SUBSTR"(v_list, 1, l_idx-1);
					v_list := "SUBSTR"(v_list, l_idx+"LENGTH"(p_sep));
					assetype_a(l_idx2):= assetype;
					dbms_output.put_line('l_idx2 : ' || l_idx2);
					dbms_output.put_line('assetype : ' ||assetype_a(l_idx2));
				ELSE
					assetype:= v_list;
					assetype_a(l_idx2):= assetype;
					dbms_output.put_line('l_idx2 : ' || l_idx2);
					dbms_output.put_line('assetype : ' ||assetype_a(l_idx2));
					EXIT;
				END IF;
	END LOOP;
	LOOP 
			l_idx1:= "INSTR"(v_list1, p_sep);
			IF l_idx1 >0 THEN
				serialnum := "SUBSTR"(v_list1, 1, l_idx1-1);
				v_list1 := "SUBSTR"(v_list1, l_idx1+"LENGTH"(p_sep));
				dbms_output.put_line('serialnum : '|| serialnum);
				l_idx2 := 1;
				v_stmt_str := 'select DISTINCT ASSETTYPE from BALANCE where KEEPFOLDER_ID = :j';
				OPEN v_bc FOR v_stmt_str USING serialnum;
					LOOP
						FETCH v_bc INTO assetype_a(l_idx2);
						EXIT WHEN v_bc%NOTFOUND;
						l_idx2 := l_idx2+1;	
					END LOOP;
				CLOSE v_bc;
				l_idx2 := l_idx2-1;
				FOR i in 1..l_idx2 LOOP
					dbms_output.put_line('assetype : ' ||assetype_a(i));
				END LOOP;
			ELSE
				serialnum:= v_list1;
				dbms_output.put_line('serialnum : '|| serialnum);
				l_idx2 := 1;
				v_stmt_str := 'select DISTINCT ASSETTYPE from BALANCE where KEEPFOLDER_ID = :j';
				OPEN v_bc FOR v_stmt_str USING v_list1;
					LOOP
						FETCH v_bc INTO assetype_a(l_idx2);
						EXIT WHEN v_bc%NOTFOUND;
						l_idx2 := l_idx2+1;	
					END LOOP;
				CLOSE v_bc;
				l_idx2 := l_idx2-1;
				FOR i in 1..l_idx2 LOOP
					dbms_output.put_line('assetype : ' ||assetype_a(i));
				END LOOP;
				EXIT;
			END IF;
	END LOOP;
END;