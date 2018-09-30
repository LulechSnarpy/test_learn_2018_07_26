package runju;

import club.iskyc.lulech.gomoku.entity.impl.Site;

public class PrintTool {
	private PrintTool(){}
	public static void PrintSites(Site[][] sites){
		for (int i=0; i<33; i++) System.out.print("-");
		System.out.println();
		for (int i=0; i<15; i++) {
			System.out.printf("%2d |",(i+1));
			for (int j=0; j<15; j++) {
				System.out.print((sites[i][j].isCloseTop()? 1:0)+"|");
				//System.out.print(sites[i][j].getTopnum()+"|");
				//System.out.print(sites[i][j].getBottomnum()+"|");
				//System.out.print(sites[i][j].getLeftnum()+"|");
				//System.out.print(sites[i][j].getRightnum()+"|");
				//System.out.print((sites[i][j].isBlank()? 0:1)+"|");
			}
			System.out.println();
			for (int j=0; j<33; j++) System.out.print("-");
			System.out.println();
		}
	}
}
