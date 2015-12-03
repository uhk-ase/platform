package cz.uhk.fim.ase.platform.model;

public class Inventory {
	private int food;
	private int painkiller;
	private int tool;
	private int hunger;
	private int health;
	private int toolHealth;
	private int finance;

	public Inventory() {
		food = 20;
		painkiller = 20;
		tool = 20;
		hunger = 100;
		health = 100;
		toolHealth = 100;
		finance = 10;
	}

	public Inventory(int food, int painkiller, int tool, int hunger,
			int health, int toolHealth, int finance) {
		this.food = food;
		this.painkiller = painkiller;
		this.tool = tool;
		this.hunger = hunger;
		this.health = health;
	}

	public void rest() {
		double r = Math.random() * 100;
		
		if (r < 80){
		setHunger(hunger -2);
		setHealth(health-5);
		}
		else{
		setHunger(hunger -4);
		setHealth(health-10);
		}
		
	}

	public void workFood() {
		double r = Math.random() * 100;
		int wFood;
		if (r < 80)
			wFood = this.food++;
		else
			wFood = this.food;

		setHunger(hunger-2);
		setHealth(health-5);
		setTool(tool-1);
		
	}

	public void workPainkiller() {

		double r = Math.random() * 100;
		int wPainkiller;
		if (r < 30)
			wPainkiller = this.painkiller++;
		else
			wPainkiller = this.painkiller;

		setHunger(hunger-2);
		setHealth(health-5);
		setTool(tool-1);
		
	}

	public void workTool() {

		double r = Math.random() * 100;
		int wTool;
		if (r < 10)
			wTool = this.tool++;
		else
			wTool = this.tool;
		
		setHunger(hunger-2);
		setHealth(health-5);
		
	}

	public void buyFood(int f,int fin) {
		
		setFood(this.food + f);
		setFinance(this.finance - fin);
		
	}

	public void buyPainkiller(int p,int fin) {
		setPainkiller(this.painkiller + p);
		setFinance(this.finance - fin);
	}

	public void buyTool(int t,int fin) {
		setTool(this.tool + t);
		setFinance(this.finance - fin);
	}

	public void sellFood(int f,int fin) {
		
		setFood(this.food - f);
		setFinance(this.finance + fin);
		
	}

	public void sellPainkiller(int p,int fin) {
		setPainkiller(this.painkiller - p);
		setFinance(this.finance + fin);
		
	}

	public void sellTool(int t,int fin) {
		setTool(this.tool - t);
		setFinance(this.finance + fin);
	}

	public void useFood() {
		
		setFood(this.food -1);
		setHunger(this.hunger +3);
		
	}

	public void usePainkiller() {
		
		setPainkiller(this.painkiller-1);
		setHealth(this.health+10);
		
	}

	public void useTool() {
		
		setTool(this.tool -1);
		
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getPainkiller() {
		return painkiller;
	}

	public void setPainkiller(int painkiller) {
		this.painkiller = painkiller;
	}

	public int getTool() {
		return tool;
	}

	public void setTool(int tool) {
		this.tool = tool;
	}

	public int getHunger() {
		return hunger;
	}

	public void setHunger(int hunger) {
		this.hunger = hunger;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getToolHealth() {
		return toolHealth;
	}

	public void setToolHealth(int toolHealth) {
		this.toolHealth = toolHealth;
	}

	public int getFinance() {
		return finance;
	}

	public void setFinance(int finance) {
		this.finance = finance;
	}

}
