import com.shyshkov.gameoflife.game.ConwayGame;
import com.shyshkov.gameoflife.model.Grid;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new File("25x25-grid.txt");
        ConwayGame game = new ConwayGame(Grid.fromStream(new FileInputStream(file)));

        while (true) {
            System.out.println(game.getGridAsText());
            System.out.println("==================");
            game.createNextGeneration();

            Thread.sleep(2000L);
        }
    }
}
