
public class Game
{
	private Grid grid;
	private int userRow;
	private int msElapsed;
	private int timesGet;
	private int timesAvoid;
	public Game()
	{
		grid = new Grid(10, 15);
		setBackground("Background.jpg");						//Background image file
		userRow = 2;
		msElapsed = 0;
		timesGet = 0;
		timesAvoid = 0;
		updateTitle();
		
		grid.setImage(new Location(userRow, 0), "stickergiant-dinosaur.gif");		//Changed user image
		
	}
	public void setBackground(String backgroundImageFile) {		//Added a background to the game
        grid.load(backgroundImageFile);
    }
	public void play()
	{
		while (!isGameOver())
		{
			grid.pause(50);						//Increased the speed of the game
			handleKeyPress();
			if (msElapsed % 300 == 0)
			{
				scrollLeft();
				populateRightEdge();
			}
			updateTitle();
			msElapsed += 100;
		}
		grid.showMessageDialog("Highest score achieved "+timesGet+"."); //Added a end message of highest score achieved after the game ends
	}
	public void handleKeyPress()
	{
		int key = grid.checkLastKeyPressed();
		if (key == 38) { // Up arrow
			grid.setImage(new Location(userRow, 0), null);  //Does not go out of bounds for upper movements
			if (userRow > 0) {
				userRow--;
			}
		}
		else if (key == 40) { // Down arrow					//Does not go out of bounds for lower movements 
			grid.setImage(new Location(userRow, 0), null);
			if (userRow < grid.getNumRows() - 1) {
				userRow++;
			}
		}
		// if the userRow is too small, (less than 0) set it to 0
		// if too large, set to bottom row
		handleCollision(new Location(userRow, 0));
		grid.setImage(new Location(userRow, 0), "stickergiant-dinosaur.gif");
	}
	public void populateRightEdge()
	{
		int randomRow = (int) (Math.random()*grid.getNumRows());
		if (Math.random() < 0.5)
			grid.setImage(new Location(randomRow, grid.getNumCols()-1),
					"mega-mushroom-icon.gif");
		else
			grid.setImage(new Location(randomRow, grid.getNumCols()-1),
					"an-ordinary-egg-egg.gif");
	}
	public void scrollLeft()
	{
		for(int i =0; i<10; i++) {
		for (int col =1; col<grid.getNumCols(); col++) {
			String image = grid.getImage(new Location(i,col));
			grid.setImage(new Location(i, col-1), image);
		}
		grid.setImage(new Location(i, grid.getNumCols()-1), null);
		}
		handleCollision(new Location(userRow,0));
		grid.setImage(new Location(userRow, 0), "stickergiant-dinosaur.gif");
	}
	public void handleCollision(Location loc)
	{
		String img = grid.getImage(loc);
		if (img == null)
			return;
		if ("mega-mushroom-icon.gif".equals(img)) {					//Changed avoid 'A' to a mushroom image
			timesAvoid++;
		}
		else if ("an-ordinary-egg-egg.gif".equals(img)) {			//Changed get 'G' to an egg image
			timesGet++;
		}
	}
	public String getScore()				//
	{
		
		return ("|Total Score: "+ (timesGet-timesAvoid+ "|"));
	}
	public void updateTitle()
	{
		grid.setTitle("DINO egg     " + getScore()+ "        |Eggs : "+ timesGet+" "+" Mushrooms : "+ timesAvoid+"|");   // added number of mushrooms captured and number of eggs captured and the total score
	}
	public boolean isGameOver()									//If number of mushroom is more than the number of eggs captured then it is a game over.
	{
		return (timesGet-timesAvoid<0);
	}
	public static void test()
	{
		Game game = new Game();
		game.play();
	}
	
	public static void main(String[] args)
	{
		test();
	}
}
