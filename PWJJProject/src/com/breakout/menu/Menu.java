package com.breakout.menu;
 
 
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
import com.breakout.game.BreakoutApp;
 
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
 
 
public class Menu extends Application
{
	private GameMenu gameMenu;
 
 
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		
		Pane root = new Pane();
		root.setPrefSize(800, 500);
 
		InputStream is = Files.newInputStream(Paths.get("res/images/menu.jpg"));
		Image img = new Image(is);
		is.close();
 
		ImageView imgView = new ImageView(img);
		imgView.setFitHeight(500);
		imgView.setFitWidth(800);
 
		gameMenu = new GameMenu();
		gameMenu.setVisible(true);
		root.getChildren().addAll(imgView, gameMenu);
 
		Scene scene = new Scene(root);
		/*scene.setOnKeyPressed(event -> {
			if(event.getCode() == KeyCode.ESCAPE)
			{
				if(!gameMenu.isVisible())
				{
					FadeTransition ft = new FadeTransition(Duration.seconds(1), gameMenu);
					ft.setFromValue(0);
					ft.setToValue(1);
					gameMenu.setVisible(true);
					ft.play();
				}
				else
				{
					FadeTransition ft = new FadeTransition(Duration.seconds(1), gameMenu);
					ft.setFromValue(1);
					ft.setToValue(0);
					ft.setOnFinished(evt -> gameMenu.setVisible(false));
					ft.play();
				}
			}
		});*/
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
 
	private class GameMenu extends Parent
	{
 
 
		public GameMenu()
		{
			VBox menu0 = new VBox(10);
			VBox menu1 = new VBox(10);
 
			menu0.setTranslateX(100);
			menu1.setTranslateX(100);
			menu0.setTranslateY(200);
			menu1.setTranslateY(200);
 
			final int offset =  600;
			menu1.setTranslateX(offset);
 
 
			MenuButton btnResume = new MenuButton("Run");
			btnResume.setOnMouseClicked(event -> {
				/*FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
				ft.setFromValue(1);
				ft.setToValue(0);
				ft.setOnFinished(evt -> setVisible(false));
				ft.play();*/

				//BreakoutApp gameApp = new BreakoutApp();
			//	ExecutorService exec = Executors.newSingleThreadExecutor();
			//	exec.execute(gameApp);
				Platform.runLater(new Runnable() {
					public void run() {
						try {
							BreakoutApp gameApp = new BreakoutApp();
							gameApp.start(new Stage());
						} catch (Exception e)
						{
							System.err.println(e);
						}
				}});
 
			});
 
			MenuButton btnOptions = new MenuButton("Options");
			btnOptions.setOnMouseClicked(event -> {
 
				getChildren().add(menu1);
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
				tt.setToX(menu0.getTranslateX() - offset);
 
				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu1);
				tt1.setToX(menu0.getTranslateX());
 
				tt.play();
				tt1.play();
 
				tt.setOnFinished(evt -> {
					getChildren().remove(menu0);
				});
 
			});
			MenuButton btnExit = new MenuButton("Exit");
			btnExit.setOnMouseClicked(event -> { System.exit(0);});
 
			MenuButton btnBack = new MenuButton("Back");
			btnBack.setOnMouseClicked(event -> {
 
				getChildren().add(menu0);
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu1);
				tt.setToX(menu1.getTranslateX() - offset);
 
				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
				tt1.setToX(menu1.getTranslateX());
 
				tt.play();
				tt1.play();
 
				tt.setOnFinished(evt -> {
					getChildren().remove(menu1);
				});
 
			});
 
			MenuButton btnSound = new MenuButton("Sound");
			MenuButton btnVideo = new MenuButton("Video");
 
			menu0.getChildren().addAll(btnResume, btnOptions, btnExit);
			menu1.getChildren().addAll(btnBack, btnSound, btnVideo);
 
			Rectangle bg = new Rectangle(800, 500);
			bg.setFill(Color.GRAY);
			bg.setOpacity(0.4);
			getChildren().addAll(bg, menu0);
 
 
		}
	}

	private static class MenuButton extends StackPane
	{
		private Text text;
 
		public MenuButton(String name)
		{
			text = new Text(name);
			text.setFont(text.getFont().font(20));
			text.setFill(Color.WHITE);
 
			Rectangle bg = new Rectangle(250, 30);
			bg.setOpacity(0.6);
			bg.setFill(Color.BLACK);
			bg.setEffect(new GaussianBlur(3.5));
 
			setAlignment(Pos.CENTER_LEFT);
			setRotate(-0.5);
			getChildren().addAll(bg, text);
 
			this.setOnMouseEntered(event -> {
				bg.setTranslateX(10);
				text.setTranslateX(10);
				bg.setFill(Color.WHITE);
				text.setFill(Color.BLACK);
			});
 
			this.setOnMouseExited(event -> {
				bg.setTranslateX(0);
				text.setTranslateX(0);
				bg.setFill(Color.BLACK);
				text.setFill(Color.WHITE);
			});
 
			DropShadow drop = new DropShadow(50, Color.WHITE);
			drop.setInput(new Glow());
 
			setOnMousePressed(event -> setEffect(drop));
			setOnMouseReleased(event -> setEffect(null));
		}
	}
 
	public static void main(String args[])
	{

		
		launch();
	}
 
}
 