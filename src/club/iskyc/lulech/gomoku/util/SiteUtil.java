package club.iskyc.lulech.gomoku.util;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import club.iskyc.lulech.gomoku.constant.ISiteUtil;
import club.iskyc.lulech.gomoku.entity.ICheckerBoard;
import club.iskyc.lulech.gomoku.entity.impl.Site;

public class SiteUtil implements ISiteUtil{
	private SiteUtil(){}
	private static SiteUtil siteUtil = new SiteUtil();
	
	synchronized public static SiteUtil instance(){
		return siteUtil;
	}
	
	public void updateBottomRight(Site[][] sites, Site site) {
		/*Stack<Site> stack = new Stack<>();
		stack.add(site);
		Site tlsite = site; 
		for (int i=0; i<4; i++) {
			tlsite = siteUtil.getTopLeft(sites, tlsite);
			if (tlsite==null) break;
			stack.push(tlsite);
		}
		while (!stack.empty()) {
			tlsite = stack.pop();
			updateBottomRightOnly(sites, tlsite);
		}*/	
	}
	
	public void updateBottomLeft(Site[][] sites, Site site) {
		/*Stack<Site> stack = new Stack<>();
		stack.add(site);
		Site tlsite = site; 
		for (int i=0; i<4; i++) {
			tlsite = siteUtil.getTopLeft(sites, tlsite);
			if (tlsite==null) break;
			stack.push(tlsite);
		}
		while (!stack.empty()) {
			tlsite = stack.pop();
			updateBottomRightOnly(sites, tlsite);
		}	*/
	}
	
	public void updateTopRight(Site[][] sites, Site site) {
		Stack<Site> stack = new Stack<>();
		stack.add(site);
		Site trsite = site; 
		for (int i=0; i<4; i++) {
			trsite = siteUtil.getTopLeft(sites, trsite);
			if (trsite==null) break;
			stack.push(trsite);
		}
		while (!stack.empty()) {
			trsite = stack.pop();
			updateBottomLeftOnly(sites, trsite);
		}	
	}
	
	public void updateBottomLeftOnly(Site[][] sites, Site site) {
		Queue<Site> queue = new LinkedList<>();
		Site blsite = site; 
		for (int i=0; i<4; i++) {
			blsite = siteUtil.getTop(sites, blsite);
			if (blsite==null) break;
			queue.add(blsite);
		}
		int num = 0;
		boolean  iscountinue= true;
		boolean isFirst = true;
		while (!queue.isEmpty()) {
			blsite = queue.poll();
			if(blsite.isBlank()){
				iscountinue = false;
				continue;
			}
			if (isFirst && site.isBlank()) {
				site.setBlackBottomLeft(blsite.isBlack());
				site.setBlack(blsite.isBlack());
				isFirst = false;
			}
			if(blsite.isBlack()^site.isBlack()) {
				if (iscountinue) site.setCloseTop(true);
				break;
			}
			num++;
		}
		site.setBottomleftnum(num);
	}
	
	
	public void updateTopLeft(Site[][] sites, Site site) {
		Stack<Site> stack = new Stack<>();
		stack.add(site);
		Site tlsite = site; 
		for (int i=0; i<4; i++) {
			tlsite = siteUtil.getTopLeft(sites, tlsite);
			if (tlsite==null) break;
			stack.push(tlsite);
		}
		while (!stack.empty()) {
			tlsite = stack.pop();
			updateBottomRightOnly(sites, tlsite);
		}		
	}
	
	public void updateBottomRightOnly(Site[][] sites, Site site) {
		Queue<Site> queue = new LinkedList<>();
		Site brsite = site; 
		for (int i=0; i<4; i++) {
			brsite = siteUtil.getTop(sites, brsite);
			if (brsite==null) break;
			queue.add(brsite);
		}
		int num = 0;
		boolean  iscountinue= true;
		boolean isFirst = true;
		while (!queue.isEmpty()) {
			brsite = queue.poll();
			if(brsite.isBlank()){
				iscountinue = false;
				continue;
			}
			if (isFirst && site.isBlank()) {
				site.setBlackBottomRight(brsite.isBlack());
				site.setBlack(brsite.isBlack());
				isFirst = false;
			}
			if(brsite.isBlack()^site.isBlack()) {
				if (iscountinue) site.setCloseTop(true);
				break;
			}
			num++;
		}
		site.setBottomrightnum(num);
	}
	
	public void updateBottom(Site[][] sites, Site site) {
		Stack<Site> stack = new Stack<>();
		stack.add(site);
		Site bsite = site; 
		for (int i=0; i<4; i++) {
			bsite = siteUtil.getBottom(sites, bsite);
			if (bsite==null) break;
			stack.push(bsite);
		}
		while (!stack.empty()) {
			bsite = stack.pop();
			updateTopOnly(sites, bsite);
		}
	}
	
	public void updateTopOnly(Site[][] sites, Site site) {
		Queue<Site> queue = new LinkedList<>();
		Site tsite = site; 
		for (int i=0; i<4; i++) {
			tsite = siteUtil.getTop(sites, tsite);
			if (tsite==null) break;
			queue.add(tsite);
		}
		int num = 0;
		boolean  iscountinue= true;
		boolean isFirst = true;
		while (!queue.isEmpty()) {
			tsite = queue.poll();
			if(tsite.isBlank()){
				iscountinue = false;
				continue;
			}
			if (isFirst && site.isBlank()) {
				site.setBlackTop(tsite.isBlack());
				site.setBlack(tsite.isBlack());
				isFirst = false;
			}
			if(tsite.isBlack()^site.isBlack()) {
				if (iscountinue) site.setCloseTop(true);
				break;
			}
			num++;
		}
		site.setTopnum(num);
	}
	
	public void updateTop(Site[][] sites, Site site) {
		Stack<Site> stack = new Stack<>();
		stack.add(site);
		Site tsite = site; 
		for (int i=0; i<4; i++) {
			tsite = siteUtil.getTop(sites, tsite);
			if (tsite==null) break;
			stack.push(tsite);
		}
		while (!stack.empty()) {
			tsite = stack.pop();
			updateBottomOnly(sites, tsite);
		}
	}
	
	public void updateBottomOnly(Site[][] sites, Site site) {
		Queue<Site> queue = new LinkedList<>();
		Site bsite = site; 
		for (int i=0; i<4; i++) {
			bsite = siteUtil.getBottom(sites, bsite);
			if (bsite==null) break;
			queue.add(bsite);
		}
		int num = 0;
		boolean  iscountinue= true;
		boolean isFirst = true;
		while (!queue.isEmpty()) {
			bsite = queue.poll();
			if(bsite.isBlank()){
				iscountinue = false;
				continue;
			}
			if (isFirst && site.isBlank()) {
				site.setBlackBottom(bsite.isBlack());
				site.setBlack(bsite.isBlack());
				isFirst = false;
			}
			if(bsite.isBlack()^site.isBlack()) {
				if (iscountinue) site.setCloseBottom(true);
				break;
			}
			num++;
		}
		site.setBottomnum(num);
	}
	
	public void updateRight(Site[][] sites, Site site) {
		Stack<Site> stack = new Stack<>();
		stack.add(site);
		Site rsite = site; 
		for (int i=0; i<4; i++) {
			rsite = siteUtil.getRight(sites, rsite);
			if (rsite==null) break;
			stack.push(rsite);
		}
		while (!stack.empty()) {
			rsite = stack.pop();
			updateLeftOnly(sites, rsite);
		}
	}
	
	public void updateLeftOnly(Site[][] sites, Site site) {
		Queue<Site> queue = new LinkedList<>();
		Site lsite = site; 
		for (int i=0; i<4; i++) {
			lsite = siteUtil.getLeft(sites, lsite);
			if (lsite==null) break;
			queue.add(lsite);
		}
		int num = 0;
		boolean iscountinue= true; 
		boolean isFirst = true;
		while (!queue.isEmpty()) {
			lsite = queue.poll();
			if (lsite.isBlank()) {
				iscountinue = false;
				continue;
			}
			if (isFirst && site.isBlank()) {
				site.setBlackLeft(lsite.isBlack());
				site.setBlack(lsite.isBlack());
				isFirst = false;
			}
			if ((lsite.isBlack()^site.isBlack())) {
				if (iscountinue) site.setCloseLeft(true);
				break;
			}
			num++;
		}
		site.setLeftnum(num);
	}
	public void updateLeft(Site[][] sites, Site site) {
		Stack<Site> stack = new Stack<>();
		stack.add(site);
		Site lsite = site; 
		for (int i=0; i<4; i++) {
			lsite = siteUtil.getLeft(sites, lsite);
			if (lsite==null) break;
			stack.push(lsite);
		}
		while (!stack.empty()) {
			lsite = stack.pop();
			updateRightOnly(sites, lsite);
		}
	}
	
	public void updateRightOnly(Site[][] sites, Site site) {
		Queue<Site> queue = new LinkedList<>();
		Site rsite = site; 
		for (int i=0; i<4; i++) {
			rsite = siteUtil.getRight(sites, rsite);
			if (rsite==null) break;
			queue.add(rsite);
		}
		int num = 0;
		boolean iscountinue= true; 
		boolean isFirst = true;
		while (!queue.isEmpty()) {
			rsite = queue.poll();
			if (rsite.isBlank()) {
				iscountinue = false;
				continue;
			}
			if (isFirst && site.isBlank()) {
				site.setBlackRight(rsite.isBlack());
				site.setBlack(rsite.isBlack());
				isFirst = false;
			}
			if ((rsite.isBlack()^site.isBlack())) {
				if (iscountinue) site.setCloseRight(true);
				break;
			}
			num++;
		}
		site.setRightnum(num);
	}
	
	public Site getLeft(Site[][] sites,Site site){
		int x = site.getX();
		int y = site.getY()-1;
		if(y>-1) return sites[x][y];
		return null;
	}
	public Site getRight(Site[][] sites,Site site){
		int x = site.getX();
		int y = site.getY()+1;
		if(y<ICheckerBoard.GRIDSIZE) return sites[x][y];
		return null;
	}
	public Site getTop(Site[][] sites,Site site){
		int x = site.getX()-1;
		int y = site.getY();
		if(x>-1) return sites[x][y];
		return null;
	}
	public Site getBottom(Site[][] sites,Site site){
		int x = site.getX()+1;
		int y = site.getY();
		if(x<ICheckerBoard.GRIDSIZE) return sites[x][y];
		return null;
	}
	public Site getTopLeft(Site[][] sites,Site site){
		int x = site.getX()-1;
		int y = site.getY()-1;
		if(x>-1&&y>-1) return sites[x][y];
		return null;
	}
	public Site getTopRight(Site[][] sites,Site site){
		int x = site.getX()-1;
		int y = site.getY()+1;
		if(y<ICheckerBoard.GRIDSIZE&&x>-1) return sites[x][y];
		return null;
	}
	public Site getBottomLeft(Site[][] sites,Site site){
		int x = site.getX()+1;
		int y = site.getY()-1;
		if(y>-1&&x<ICheckerBoard.GRIDSIZE) return sites[x][y];
		return null;
	}
	public Site getBottomRight(Site[][] sites,Site site){
		int x = site.getX()+1;
		int y = site.getY()+1;
		if(x<ICheckerBoard.GRIDSIZE&&y<ICheckerBoard.GRIDSIZE) return sites[x][y];
		return null;
	}
}
