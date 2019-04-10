<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="glf" uri="/WEB-INF/tag/glf.tld"%>
<!DOCTYPE html>
<html>
<head>
<title>产品信息维护新增</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport' />
<c:import url="./../../header_resource.jsp" charEncoding="UTF-8"></c:import>
</head>
<body class="fix-header">
      <div class ="page-wrapper" id="pagediv">
          <div class="container-fluid">
              <div class="row bg-title">
                  <div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
                      <h4 class="page-title">产品信息维护</h4>
                  </div>
                  <glf:breadcrumb value="11010100"/>
              </div>
              <div class="row">
		            <form id="addForm" action="${pageContext.request.contextPath}/product/maintain/add" method="post" class="form-horizontal">
			            <input name="backUrl" id="backUrl" value="${backUrl}" type='hidden'/>
			            <div class="col-md-12">
	                		<div class="panel-group" role="tablist" aria-multiselectable="true">
	                			<!-- 第一部分 -->
	                			<div class="panel panel-default">
			                        <div class="panel-heading" role="tab" id="headingOne">
			                            <h4 class="panel-title"> <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne" class="font-bold"> 基础资料</a> </h4>
			                        </div>
			                        <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
				                        <div class="panel-body">
				                        
	                                		<div class="row">
	                                			<!-- 是否子产品 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="isParent" fieldText="是否子产品"/></label>
													  	<div class="col-md-9">
													  		<select class="form-control" id="isParent" name="isParent" validate='required:true'>
																<glf:dictOptions dictId="100000" value="${isParent}"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 是否子产品 -->
	                                			<!-- 虚拟产品 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="isVpd" fieldText="虚拟产品"/></label>
													  	<div class="col-md-9">
															<select class="form-control" id="isVpd" name="isVpd" validate='required:true'>
																<glf:dictOptions dictId="100000" value="N"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 虚拟产品 -->
												<!-- 产品代码 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="investCode" fieldText="产品代码"/></label>
													  	<div class="col-md-9">
															<input id="investCode" name="investCode" autocomplete="off" value="${investCode}" validate='required:true,maxlength:20,alnum:true' type="text" class="form-control" placeholder="产品代码"/>
														</div>
													</div>
												</div>	
												<!-- 产品代码 -->
												<!-- 产品名称 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="investName" fieldText="产品名称"/></label>
													  	<div class="col-md-9">
													  		<input id="investName" name="investName" autocomplete="off" value="${investName}" validate='required:true,maxlength:100' type="text" class="form-control" placeholder="产品名称"/>
														</div>
													</div>
												</div>	
												<!-- 产品名称 -->
												<!-- 产品模式 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="investFlag" fieldText="产品模式"/></label>
													  	<div class="col-md-9">
															<select class="form-control" id="investFlag" name="investFlag" validate='required:true'>
																<glf:dictOptions dictId="100041" value="${investFlag}"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 产品模式 -->
												<!-- 所属组合 -->
												<div class="col-md-6" style="display:none;">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="investSeriesCode" fieldText="所属组合"/></label>
													  	<div class="col-md-9">
				                                            <select class="form-control" id="investSeriesCode" name="investSeriesCode" validate='required:true'>
																<glf:selectItems list="${acc_product_list}" value="${acc_product_list}" type="true"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 所属组合 -->
												<!-- 运作方式 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="operateType" fieldText="运作方式"/></label>
													  	<div class="col-md-9">
													  		<select class="form-control" id="operateType" name="operateType" validate='required:true'>
																<glf:dictOptions dictId="100043" value="${operateType}"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 运作方式 -->
												<!-- 投资通道 --
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="cusNumber" fieldText="投资通道"/></label>
													  	<div class="col-md-9">
															<select class="form-control"  validate='required:true'>
																<glf:selectItems list="${liquidationList}" value="${investChannel}" type="true"/>
				                                            </select>
														</div>
													</div>
												</div>	
												!-- 投资通道 -->
												
												<div class="col-md-6">
													<div class="form-group">
														<label class="control-label col-md-3">投资通道</label>
														<input type="hidden" id="investChannel" name="investChannel" value="${investChannel}"/>
														<div class="col-md-9">
															<select class="form-control select2" multiple="multiple" data-placeholder="请选择投资通道" style="width: 100%;" validate='required:true'>
																<glf:selectItems list="${liquidationList}" value="${investChannel}" type="true"/>
															</select>
														</div>
													</div>
												</div>
												
												<!-- 托管账户 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="cusNumber" fieldText="托管账户"/></label>
													  	<div class="col-md-9">
															<select class="form-control" id="custodyAccount" name="custodyAccount" validate='required:true'>
																<glf:selectItems list="${acc_liquidation_user}" value="${custodyAccount}" type="true"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 托管账户 -->
												<!-- 报送监管口径 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="reportSupervision" fieldText="报送监管口径"/></label>
													  	<div class="col-md-9">
													  		<select class="form-control" id="reportSupervision" name="reportSupervision" validate="required:true">
																<glf:dictOptions dictId="100042" value="${reportSupervision}"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 报送监管口径 -->
												<!-- 偏离度检核规则 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="deviationCheckRules" fieldText="偏离度检核规则"/></label>
													  	<div class="col-md-9">
													  		<select class="form-control" id="deviationCheckRules" name="deviationCheckRules" validate="required:true">
																<glf:dictOptions dictId="100025" value="3"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 偏离度检核规则 -->
												<!-- 所属用户 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="OWNER_USER" fieldText="所属用户"/></label>
													  	<div class="col-md-9">
															<input id="OWNER_USER" name="OWNER_USER" autocomplete="off" value="${OWNER_USER}" readonly="readonly" type="text" class="form-control" placeholder="所属用户 "/> 
														</div>
													</div>
												</div>
												<!-- 所属用户 -->
			                        		</div>
			                        	</div>
			                        </div>
			                    </div>
			                    <!-- 第二部分 -->
			                    <div class="panel panel-default">
				                     <div class="panel-heading" role="tab" id="headingTwo">
				                         <h4 class="panel-title"> <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo" class="font-bold"> 基础计算 </a> </h4>
				                     </div>
				                     <div id="collapseTwo" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingTwo">
					                     <div class="panel-body">
		                                	<div class="row">
	                                			<!-- 产品起息日 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="startCouponDate" fieldText="产品起息日"/></label>
													  	<div class="col-md-9">
														  	<div class="input-group">
					                                            <input type="text" class="form-control datepicker" id="startCouponDate" validate='required:true' name="startCouponDate" placeholder="YYYY/MM/DD"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
					                                        </div>
														</div>
													</div>
												</div>	
												<!-- 产品起息日 -->
												<!-- 产品到期日 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="maturityDate" fieldText="产品到期日"/></label>
													  	<div class="col-md-9">
													  		<div class="input-group">
					                                            <input type="text" class="form-control datepicker" id="maturityDate" validate='required:true,between:["startCouponDate","maturityDate"]' value="${maturityDate}" name="maturityDate" name="maturityDate" placeholder="YYYY/MM/DD"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
					                                        </div>
														</div>
													</div>
												</div>	
												<!-- 产品到期日 -->
												<!-- 产品兑付日 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="winupPaydate" fieldText="产品兑付日"/></label>
													  	<div class="col-md-9">
													  		<div class="input-group">
					                                            <input type="text" class="form-control datepicker" id="winupPaydate" validate='required:true,between:["maturityDate","winupPaydate"]' name="winupPaydate" placeholder="YYYY/MM/DD"> <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
					                                        </div>
														</div>
													</div>
												</div>	
												<!-- 产品兑付日 -->
												<!-- 产品成立金额(万) -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="foundAmt" fieldText="产品成立金额(万)"/></label>
													  	<div class="col-md-9">
															<input id="foundAmt" name="foundAmt" autocomplete="off" value="${foundAmt}" validate='required:true,currency:[16,6]' type="text" class="form-control" placeholder="产品成立金额(万)"/>
														</div>
													</div>
												</div>	
												<!-- 产品成立金额(万) -->
												<!-- 分红类型 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="divType" fieldText="分红类型"/></label>
													  	<div class="col-md-9">
													  		<select class="form-control" id="divType" name="divType" validate='required:true'>
																<glf:dictOptions dictId="100046" value="${divType}"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 分红类型 -->
												<!-- 分红频率 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="divFrequency" fieldText="分红频率"/></label>
													  	<div class="col-md-9">
													  		<select class="form-control" id="divFrequency" name="divFrequency" validate='required:true'>
																<glf:dictOptions dictId="100047" value="${divFrequency}"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 分红频率 -->
												<!-- 分红月 -->
												<div class="col-md-6" style="display:none;">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="divYearSeason" fieldText="分红月"/></label>
													  	<div class="col-md-9">
															<input id="divYearSeason" name="divYearSeason" autocomplete="off" value="${divYearSeason}" validate='required:true,maxlength:2,digits:true' type="text" class="form-control" placeholder="分红月"/>
														</div>
													</div>
												</div>	
												<!-- 分红月 -->
												<!-- 分红日 -->
												<div class="col-md-6" style="display:none;">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="divSettlementDay" fieldText="分红日"/></label>
													  	<div class="col-md-9">
															<input id="divSettlementDay" name="divSettlementDay" autocomplete="off" value="${divSettlementDay}" validate='required:true,maxlength:2,digits:true' type="text" class="form-control" placeholder="分红日"/>
														</div>
													</div>
												</div>	
												<!-- 分红日 -->
												<!-- 产品预期收益率% -->
												<div class="clearfix">
													<div class="col-md-6">
														<div class="form-group">
		                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="expectedReturn" fieldText="产品预期收益率%"/></label>
														  	<div class="col-md-9">
																<input id="expectedReturn" name="expectedReturn" autocomplete="off" value="${expectedReturn}" validate='required:true,,currency:[16,6]' type="text" class="form-control" placeholder="产品预期收益率%"/>
															</div>
														</div>
													</div>
														
													<!-- 产品预期收益率% -->
													<!-- 净值类型 -->
													<div class="col-md-6">
														<div class="form-group">
		                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="isNvlType" fieldText="净值类型"/></label>
														  	<div class="col-md-9">
														  		<select class="form-control" id="isNvlType" name="isNvlType" validate='required:true'>
																	<glf:dictOptions dictId="100044" value="${isNvlType}"/>
					                                            </select>
															</div>
														</div>
													</div>
												</div>	
												<!-- 净值类型 -->
												<!-- 申赎使用净值 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="isNeedNav" fieldText="申赎使用净值"/></label>
													  	<div class="col-md-9">
													  		<select class="form-control" id="isNeedNav" name="isNeedNav" validate='required:true'>
																<glf:dictOptions dictId="100045" value="${isNeedNav}"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 申赎使用净值 -->
												<!-- 计息基准 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="calaType" fieldText="计息基准"/></label>
													  	<div class="col-md-9">
													  		<select class="form-control" id="calaType" name="calaType" validate='required:true'>
																<glf:dictOptions dictId="110010" value="${calaType}"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 计息基准 -->
	                                		</div>
	                                	</div>
	                                </div>
	                			</div>
	                			
	                			<!-- 第三部分 -->
	                			<div class="panel panel-default">
				                     <div class="panel-heading" role="tab" id="headingThree">
				                         <h4 class="panel-title"> <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="true" aria-controls="collapseThree" class="font-bold"> 通用管理信息 </a> </h4>
				                     </div>
				                     <div id="collapseThree" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingThree">
					                     <div class="panel-body">
		                                	<div class="row">
	                                			<!-- 募集方式 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="collectType" fieldText="募集方式"/></label>
													  	<div class="col-md-9">
													  		<select class="form-control" id="collectType" name="collectType" validate='required:true'>
																<glf:dictOptions dictId="110009" value="1"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 募集方式 -->
												<!-- 投资性质 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="investNature" fieldText="投资性质"/></label>
													  	<div class="col-md-9">
													  		<select class="form-control" id="investNature" name="investNature" validate='required:true'>
																<glf:dictOptions dictId="100048" value="1"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 投资性质 -->
												<!-- 产品期限(天) -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="productTerm" fieldText="产品期限(天)"/></label>
													  	<div class="col-md-9">
															<input id="productTerm" name="productTerm" autocomplete="off" value="${productTerm}" validate='maxlength:22' type="text" class="form-control" placeholder="产品期限(天)"/>
														</div>
													</div>
												</div>	
												<!-- 产品期限(天) -->
												<!-- 币种 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="curCode" fieldText="币种"/></label>
													  	<div class="col-md-9">
													  		<select class="form-control" id="curCode" name="curCode" validate='required:true'>
																<glf:dictOptions dictId="110011" value="CNY"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 币种 -->
	                                		</div>
	                                	</div>
	                                </div>
	                			</div>
	                			
                				<!-- 第四部分 -->
                				<div class="panel panel-default">
				                     <div class="panel-heading" role="tab" id="headingFour">
				                         <h4 class="panel-title"> <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="true" aria-controls="collapseFour" class="font-bold"> 中债登申报登记表 </a> </h4>
				                     </div>
				                     <div id="collapseFour" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingFour">
					                     <div class="panel-body">
		                                	<div class="row">
		                                		<!-- 产品收益类型 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="incomeType" fieldText="产品收益类型"/></label>
													  	<div class="col-md-9">
													  		<select class="form-control" id="incomeType" name="incomeType" validate='required:true'>
																<glf:dictOptions dictId="100050" value="${incomeType}"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 产品收益类型 -->
												<!-- 目标客户类型 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="investerChannel" fieldText="目标客户类型"/></label>
													  	<div class="col-md-9">
													  		<select class="form-control" id="investerChannel" name="investerChannel" validate='required:true'>
																<glf:dictOptions dictId="100051" value="${investerChannel}"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 目标客户类型 -->
												<!-- 产品品牌 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="investBrand" fieldText="产品品牌"/></label>
													  	<div class="col-md-9">
															<input id="investBrand" name="investBrand" autocomplete="off" value="${investBrand}" validate='required:true,maxlength:50' type="text" class="form-control" placeholder="产品品牌"/>
														</div>
													</div>
												</div>	
												<!-- 产品品牌 -->
												<!-- 产品期次 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="investPeriod" fieldText="产品期次"/></label>
													  	<div class="col-md-9">
															<input id="investPeriod" name="investPeriod" autocomplete="off" value="${investPeriod}" validate='required:true,digits:true,maxlength:3' type="text" class="form-control" placeholder="产品期次"/>
														</div>
													</div>
												</div>	
												<!-- 产品期次 -->
												<!-- 开放周期(天) -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="openDays" fieldText="开放周期(天)"/></label>
													  	<div class="col-md-9">
															<input id="openDays" name="openDays" autocomplete="off" value="${openDays}" validate='required:true,digits:true,maxlength:3' type="text" class="form-control" placeholder="开放周期(天)"/>
														</div>
													</div>
												</div>	
												<!-- 开放周期(天) -->
												<!-- 产品登记编码 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="investRegisterCode" fieldText="产品登记编码"/></label>
													  	<div class="col-md-9">
															<input id="investRegisterCode" name="investRegisterCode" autocomplete="off" value="${investRegisterCode}" validate='required:true,maxlength:25' type="text" class="form-control" placeholder="产品登记编码"/>
														</div>
													</div>
												</div>	
												<!-- 产品登记编码 -->
												<!-- 是否现金管理类产品 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="cashManagement" fieldText="是否现金管理类产品"/></label>
													  	<div class="col-md-9">
													  		<select class="form-control" id="cashManagement" name="cashManagement" validate='required:true'>
																<glf:dictOptions dictId="100000" value="${cashManagement}"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 是否现金管理类产品 -->
	                                		</div>
	                                	</div>
	                                </div>
	                			</div>
                				<!-- 第五部分 -->
                				<div class="panel panel-default">
				                     <div class="panel-heading" role="tab" id="headingFive">
				                         <h4 class="panel-title"> <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFive" aria-expanded="true" aria-controls="collapseFive" class="font-bold"> 人行报表 </a> </h4>
				                     </div>
				                     <div id="collapseFive" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingFive">
					                     <div class="panel-body">
		                                	<div class="row">
	                                			<!-- 客户类型 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="custType" fieldText="客户类型"/></label>
													  	<div class="col-md-9">
													  		<select class="form-control" id="custType" name="custType" validate='required:true'>
																<glf:dictOptions dictId="110012" value="${custType}"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 客户类型 -->
												<!-- 管理方式 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="manageType" fieldText="管理方式"/></label>
													  	<div class="col-md-9">
													  		<select class="form-control" id="manageType" name="manageType" validate='required:true'>
																<glf:dictOptions dictId="100069" value="${manageType}"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 管理方式 -->
												<!-- 受托职责 -->
												<div class="col-md-6">
													<div class="form-group">
	                                                	<label class="control-label col-md-3"><glf:attributeLabel menuCode="11010100" fieldModel="fiduciaryDuty" fieldText="受托职责"/></label>
													  	<div class="col-md-9">
													  		<select class="form-control" id="fiduciaryDuty" name="fiduciaryDuty" validate='required:true'>
																<glf:dictOptions dictId="100070" value="${fiduciaryDuty}"/>
				                                            </select>
														</div>
													</div>
												</div>	
												<!-- 受托职责 -->
	                                		</div>
	                                	</div>
	                                </div>
	                			</div>
			                    <!--自定义字段-start -->
								<glf:attributeField menuCode="11010100"/>
			                    <!--自定义字段-end -->
							</div>
						</div>
						
                        <div class="form-actions">
                            <div class="row">
                                <div class="col-md-6">&nbsp;</div>
                                <div class="col-md-6">
                                    <div class="row">
                                        <div class="col-md-offset-3 col-md-9">
											<glf:auth value="11010101">
												<button id="button" type="submit" mode='submit' class="btn btn-primary">提交</button>
											</glf:auth>
											<a class='btn btn-default' href="${pageContext.request.contextPath}/product/maintain/browse?backUrl=${backUrl}">返回 </a>
										</div>
                                    </div>
                                </div>
                            </div>
                        </div>
					</form>
			  </div>
          </div>
          <!-- /#page-wrapper -->
      </div>
      <!-- /#wrapper -->
</body>
<c:import url="./../../footer_resource.jsp" charEncoding="UTF-8"></c:import>
<c:import url="./../../error.jsp" charEncoding="UTF-8"></c:import>

<script type="text/javascript">
	jQuery('.datepicker').datepicker({
	    autoclose: true,
	    todayHighlight: true,
	    language: 'zh-CN',
	});
	$('.select2').select2({})
	jQuery(function(){
		
		//是否子产品
		$("#isParent").change(function(){
			//如果是：产品模式 （不展示该字段）
			if($(this).val()=="Y"){
				$("#investFlag").parent().parent().parent().hide();//产品模式
				$("#investSeriesCode").parent().parent().parent().show();
				$("#isVpd").parent().parent().parent().hide();
			}else{
				$("#investFlag").parent().parent().parent().show();//产品模式
				$("#investSeriesCode").parent().parent().parent().hide();
				$("#isVpd").parent().parent().parent().show();
			}
		})
		
		//产品模式是否为"单一",如果是：报送监管口径（不展示该字段） ；反之， 产品起息日、产品到期日、产品兑付日、产品成立金额（万）、产品预期收益率%（不展示该字段）
		$("#investFlag").change(function(){
			if($(this).val()=="2"){
				$("#reportSupervision").parent().parent().parent().hide();//报送监管口径
				$("#startCouponDate").parent().parent().parent().parent().show(); //产品起息日
				$("#maturityDate").parent().parent().parent().parent().show(); //产品到期日
				$("#winupPaydate").parent().parent().parent().parent().show(); //产品兑付日
				$("#foundAmt").parent().parent().parent().show(); //产品成立金额（万）
				$("#isNvlType").parent().parent().parent().show(); //净值类型
				//当产品为单一型产品时，产品兑付日默认展示产品到期日
				$("#winupPaydate").val($("#maturityDate").val());
			}else{
				$("#reportSupervision").parent().parent().parent().show();//报送监管口径
				$("#startCouponDate").parent().parent().parent().parent().hide(); //产品起息日
				$("#maturityDate").parent().parent().parent().parent().hide(); //产品到期日
				$("#winupPaydate").parent().parent().parent().parent().hide(); //产品兑付日
				$("#foundAmt").parent().parent().parent().hide(); //产品成立金额（万）
				//当产品模式为组合型；运作方式封闭式净值型、开放式净值型时 ，默认净值类型不显示
				var operateType = $("#operateType").val();
				if(operateType=="2"||operateType=="4"){
					$("#isNvlType").parent().parent().parent().hide(); //净值类型
				}else{
					$("#isNvlType").parent().parent().parent().show(); //净值类型
				}
			}
		})
		
		//判断运作方式
		$("#operateType").change(function(){
			//当为封闭式净值型、封闭式非净值型时；页面展示产品期限（天）；开放周期（天）不显示
			if($(this).val()=="1"||$(this).val()=="2"){
				$("#productTerm").parent().parent().parent().show(); //产品期限（天）
				$("#openDays").parent().parent().parent().hide(); //开放周期（天）
				//当产品为封闭式产品时，分红频率项默认显示到期一次性
				$("#divFrequency").val(5); //分红频率
			}else{
				$("#productTerm").parent().parent().parent().hide(); //产品期限（天）
				$("#openDays").parent().parent().parent().show(); //开放周期（天）
				$("#divFrequency").val(""); //分红频率
			}
			//当为开放式净值型时，显示是否现金管理类产品
			if($(this).val()=="3")
				$("#cashManagement").parent().parent().parent().show(); //是否现金管理类产品
			else
				$("#cashManagement").parent().parent().parent().hide(); //是否现金管理类产品
			//封闭式非净值型、开放式非净值型；净值类型-货币型 ;申赎使用净值不显示
			if($(this).val()=="2"||$(this).val()=="4"){
				if($("#isNvlType").val()=="2"){
					$("#isNeedNav").parent().parent().parent().hide(); //申赎使用净值
					$("#isNvlType").parent().parent().parent().show(); //净值类型
				}else{
					$("#isNeedNav").parent().parent().parent().show(); //申赎使用净值
					$("#isNvlType").parent().parent().parent().hide(); //净值类型
				}
			}else{
				$("#isNeedNav").parent().parent().parent().hide(); //申赎使用净值
				$("#isNvlType").parent().parent().parent().show(); //净值类型
			}
		})
		
		//净值类型判断
		$("#isNvlType").change(function(){
			//净值类型为净值，是否现金管理类产品显示为否
			if($(this).val()=="1"){
				$("#cashManagement").val("N");//是否现金管理类产品
				$("#divType").parent().parent().parent().hide(); //分红类型
				$("#divFrequency").parent().parent().parent().hide(); //分红频率
				$("#divYearSeason").parent().parent().parent().hide();//月
				$("#divSettlementDay").parent().parent().parent().hide();//日
				$("#incomeType").parent().parent().parent().hide(); //产品收益类型
			}else{
				$("#cashManagement").val("Y");//是否现金管理类产品
				$("#divType").parent().parent().parent().show(); //分红类型
				$("#divFrequency").parent().parent().parent().show(); //分红频率
				$("#incomeType").parent().parent().parent().show(); //产品收益类型
				if($(this).val()=="2"||$(this).val()=="4")
					$("#isNeedNav").parent().parent().parent().hide(); //申赎使用净值
				else
					$("#isNeedNav").parent().parent().parent().show(); //申赎使用净值
			}
		})
		
		//分红频率判断是否是否为"日结"
		$("#divFrequency").change(function(){
			if($(this).val()=="1"){//日结
				$("#divYearSeason").parent().parent().parent().hide();//月
				$("#divSettlementDay").parent().parent().parent().show();//日
			}else if($(this).val()=="2"){//月结
				$("#divYearSeason").parent().parent().parent().hide();//月
				$("#divSettlementDay").parent().parent().parent().show();//日
			}else if($(this).val()=="3"){//季结
				$("#divYearSeason").parent().parent().parent().show();//月
				$("#divSettlementDay").parent().parent().parent().show();//日
			}else if($(this).val()=="4"){//自定义周期（天）
				$("#divYearSeason").parent().parent().parent().show();//月
				$("#divSettlementDay").parent().parent().parent().show();//日
			}else if($(this).val()=="5"){//到期一次性
				$("#divYearSeason").parent().parent().parent().show();//月
				$("#divSettlementDay").parent().parent().parent().show();//日
			}else{
				$("#divYearSeason").parent().parent().parent().hide();//月
				$("#divSettlementDay").parent().parent().parent().hide();//日
			}
		})
		
		//当产品为单一型产品时，产品兑付日默认展示产品到期日
		$("#maturityDate").change(function(){
			if($("#investFlag").val()=="2"){
				$("#winupPaydate").val($("#maturityDate").val());
			}
		})
		
		//产品期限
		$(document).on('click','.day',function(){
			var start_data = parseInt($("#startCouponDate").val());
			var end_data = parseInt($("#maturityDate").val());
			if(end_data>=start_data)
				$("#productTerm").val((end_data-start_data)+1);
			else
				$("#productTerm").val("");
		})
		
		$(document).on('change','.select2',function(){
			var arr = [];
			$(".select2").find("option:selected").each(function(){
				arr.push($(this).val());
			})
			if(arr.length>0){
				$("#investChannel").val(arr);
			}
		})
		
	})
	
</script>
</html>
