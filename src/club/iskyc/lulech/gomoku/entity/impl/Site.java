package club.iskyc.lulech.gomoku.entity.impl;

public class Site {
	private int x = -1;
	private int y = -1;
	private boolean isBlack = false;
	private boolean isBlank = true;
	
	
	private int topnum = 0;
	private int bottomnum = 0;
	private int leftnum = 0;
	private int rightnum = 0;
	private int topleftnum = 0;
	private int toprightnum = 0;
	private int bottomleftnum = 0;
	private int bottomrightnum = 0;
	
	private boolean isBlackTop = false;
	private boolean isBlackBottom = false;
	private boolean isBlackLeft = false;
	private boolean isBlackRight = false;
	private boolean isBlackTopLeft = false;
	private boolean isBlackTopRight = false;
	private boolean isBlackBottomLeft = false;
	private boolean isBlackBottomRight = false;
	
	private boolean isCloseTop = false;
	private boolean isCloseBottom = false;
	private boolean isCloseLeft = false;
	private boolean isCloseRight = false;
	private boolean isCloseTopLeft = false;
	private boolean isCloseTopRight = false;
	private boolean isCloseBottomLeft = false;
	private boolean isCloseBottomRight = false;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isBlack() {
		return isBlack;
	}
	public void setBlack(boolean isBlack) {
		this.isBlack = isBlack;
	}
	public boolean isBlank() {
		return isBlank;
	}
	public void setBlank(boolean isBlank) {
		this.isBlank = isBlank;
	}
	public int getTopnum() {
		return topnum;
	}
	public void setTopnum(int topnum) {
		this.topnum = topnum;
	}
	public int getBottomnum() {
		return bottomnum;
	}
	public void setBottomnum(int bottomnum) {
		this.bottomnum = bottomnum;
	}
	public int getLeftnum() {
		return leftnum;
	}
	public void setLeftnum(int leftnum) {
		this.leftnum = leftnum;
	}
	public int getRightnum() {
		return rightnum;
	}
	public void setRightnum(int rightnum) {
		this.rightnum = rightnum;
	}
	public int getTopleftnum() {
		return topleftnum;
	}
	public void setTopleftnum(int topleftnum) {
		this.topleftnum = topleftnum;
	}
	public int getToprightnum() {
		return toprightnum;
	}
	public void setToprightnum(int toprightnum) {
		this.toprightnum = toprightnum;
	}
	public int getBottomleftnum() {
		return bottomleftnum;
	}
	public void setBottomleftnum(int bottomleftnum) {
		this.bottomleftnum = bottomleftnum;
	}
	public int getBottomrightnum() {
		return bottomrightnum;
	}
	public void setBottomrightnum(int bottomrightnum) {
		this.bottomrightnum = bottomrightnum;
	}
	public boolean isCloseTop() {
		return isCloseTop;
	}
	public void setCloseTop(boolean isCloseTop) {
		this.isCloseTop = isCloseTop;
	}
	public boolean isCloseBottom() {
		return isCloseBottom;
	}
	public void setCloseBottom(boolean isCloseBottom) {
		this.isCloseBottom = isCloseBottom;
	}
	public boolean isCloseLeft() {
		return isCloseLeft;
	}
	public void setCloseLeft(boolean isCloseLeft) {
		this.isCloseLeft = isCloseLeft;
	}
	public boolean isCloseRight() {
		return isCloseRight;
	}
	public void setCloseRight(boolean isCloseRight) {
		this.isCloseRight = isCloseRight;
	}
	public boolean isCloseTopLeft() {
		return isCloseTopLeft;
	}
	public void setCloseTopLeft(boolean isCloseTopLeft) {
		this.isCloseTopLeft = isCloseTopLeft;
	}
	public boolean isCloseTopRight() {
		return isCloseTopRight;
	}
	public void setCloseTopRight(boolean isCloseTopRight) {
		this.isCloseTopRight = isCloseTopRight;
	}
	public boolean isCloseBottomLeft() {
		return isCloseBottomLeft;
	}
	public void setCloseBottomLeft(boolean isCloseBottomLeft) {
		this.isCloseBottomLeft = isCloseBottomLeft;
	}
	public boolean isCloseBottomRight() {
		return isCloseBottomRight;
	}
	public void setCloseBottomRight(boolean isCloseBottomRight) {
		this.isCloseBottomRight = isCloseBottomRight;
	}
	public boolean isBlackTop() {
		return isBlackTop;
	}
	public void setBlackTop(boolean isBlackTop) {
		this.isBlackTop = isBlackTop;
	}
	public boolean isBlackBottom() {
		return isBlackBottom;
	}
	public void setBlackBottom(boolean isBlackBottom) {
		this.isBlackBottom = isBlackBottom;
	}
	public boolean isBlackLeft() {
		return isBlackLeft;
	}
	public void setBlackLeft(boolean isBlackLeft) {
		this.isBlackLeft = isBlackLeft;
	}
	public boolean isBlackRight() {
		return isBlackRight;
	}
	public void setBlackRight(boolean isBlackRight) {
		this.isBlackRight = isBlackRight;
	}
	public boolean isBlackTopLeft() {
		return isBlackTopLeft;
	}
	public void setBlackTopLeft(boolean isBlackTopLeft) {
		this.isBlackTopLeft = isBlackTopLeft;
	}
	public boolean isBlackTopRight() {
		return isBlackTopRight;
	}
	public void setBlackTopRight(boolean isBlackTopRight) {
		this.isBlackTopRight = isBlackTopRight;
	}
	public boolean isBlackBottomLeft() {
		return isBlackBottomLeft;
	}
	public void setBlackBottomLeft(boolean isBlackBottomLeft) {
		this.isBlackBottomLeft = isBlackBottomLeft;
	}
	public boolean isBlackBottomRight() {
		return isBlackBottomRight;
	}
	public void setBlackBottomRight(boolean isBlackBottomRight) {
		this.isBlackBottomRight = isBlackBottomRight;
	}
}
