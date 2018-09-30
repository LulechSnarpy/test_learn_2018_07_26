package runju;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import club.iskyc.lulech.gomoku.entity.ICheckerBoard;
import club.iskyc.lulech.gomoku.entity.impl.Site;
import club.iskyc.lulech.gomoku.util.SiteUtil;

public class CheckBoard {
	private Site[][] sites = new Site[ICheckerBoard.GRIDSIZE][ICheckerBoard.GRIDSIZE];
	private boolean[][] vis = new boolean[ICheckerBoard.GRIDSIZE][ICheckerBoard.GRIDSIZE];
	private SiteUtil siteUtil = SiteUtil.instance();
	public CheckBoard(){
		init();
	}
	private void init(){
		for (int i=0; i<ICheckerBoard.GRIDSIZE; i++){
			for (int j=0; j<ICheckerBoard.GRIDSIZE; j++) {
				sites[i][j] = new Site();
				sites[i][j].setX(i);
				sites[i][j].setY(j);
			}
		}
		for (int i=0; i<ICheckerBoard.GRIDSIZE; i++){
			sites[i][0].setCloseLeft(true);
			sites[i][0].setCloseTopLeft(true);
			sites[i][0].setCloseBottomLeft(true);
			sites[i][ICheckerBoard.GRIDSIZE-1].setCloseBottom(true);
			sites[i][ICheckerBoard.GRIDSIZE-1].setCloseBottomLeft(true);
			sites[i][ICheckerBoard.GRIDSIZE-1].setCloseBottomRight(true);
			sites[0][i].setCloseTop(true);
			sites[0][i].setCloseTopLeft(true);
			sites[0][i].setCloseBottomLeft(true);
			sites[i][ICheckerBoard.GRIDSIZE-1].setCloseRight(true);
			sites[i][ICheckerBoard.GRIDSIZE-1].setCloseTopRight(true);
			sites[i][ICheckerBoard.GRIDSIZE-1].setCloseBottomRight(true);
		}
	}
	
	public void setStone(Stone stone){
		int x = stone.getX();
		int y = stone.getY();
		vis[x][y] = true;
		Site site = sites[x][y];
		site.setBlank(false);
		site.setBlack(stone.isBlack());
		updateSites(site);
	}
	
	private void updateSites(Site site) {
		siteUtil.updateLeft(sites, site);
		siteUtil.updateRight(sites, site);
		siteUtil.updateTop(sites, site);
		siteUtil.updateBottom(sites, site);
		siteUtil.updateTopLeft(sites, site);
		siteUtil.updateTopRight(sites, site);
		siteUtil.updateBottomLeft(sites, site);
		siteUtil.updateBottomRight(sites, site);
	}
	
	public Site[][] getSites() {
		return sites;
	}
	public void setSites(Site[][] sites) {
		this.sites = sites;
	}
	public boolean[][] getVis() {
		return vis;
	}
	public void setVis(boolean[][] vis) {
		this.vis = vis;
	}
}
