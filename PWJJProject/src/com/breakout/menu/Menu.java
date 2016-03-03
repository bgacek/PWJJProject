package com.breakout.menu;
 
 
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.breakout.game.BreakoutApp;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
 
 
public class Menu extends Application implements Runnable
{
	private GameMenu gameMenu;
 
 
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		Pane root = new Pane();
		root.setPrefSize(640, 960);
		InputStream is = Files.newInputStream(Paths.get("res/images/menu.jpg"));
		Image img = new Image(is);
		is.close();
 
		ImageView imgView = new ImageView(img);
		imgView.setFitHeight(960);
		imgView.setFitWidth(640);
 
		gameMenu = new GameMenu();
		gameMenu.setVisible(true);
		root.getChildren().addAll(imgView, gameMenu);
 
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	private class GameMenu extends Parent
	{

		public GameMenu()
		{
			VBox menu0 = new VBox(10);
			VBox menu1 = new VBox(10);
			VBox menu2 = new VBox(10);
 
			menu0.setTranslateX(100);
			menu1.setTranslateX(100);
			menu2.setTranslateX(100);
			menu0.setTranslateY(200);
			menu1.setTranslateY(200);
			menu2.setTranslateY(200);
 
			final int offset =  600;
			menu1.setTranslateX(offset);
			menu2.setTranslateX(offset);
 
			MenuButton btnResume = new MenuButton("Run");
			btnResume.setOnMouseClicked(event -> {
		
				getChildren().add(menu2);
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
				tt.setToX(menu0.getTranslateX() - offset);
 
				TranslateTransition tt2 = new TranslateTransition(Duration.seconds(0.5), menu2);
				tt2.setToX(menu0.getTranslateX());
 
				tt.play();
				tt2.play();
 
				tt.setOnFinished(evt -> {
					getChildren().remove(menu0);
				});
			});
			
			MenuButton btnBackFromStart = new MenuButton("Back");
			btnBackFromStart.setOnMouseClicked(event -> {
 
				getChildren().add(menu0);
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu2);
				tt.setToX(menu2.getTranslateX() - offset);
 
				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menu0);
				tt1.setToX(menu2.getTranslateX());
 
				tt.play();
				tt1.play();
 
				tt.setOnFinished(evt -> {
					getChildren().remove(menu2);
				});
 
			});

			MenuButton btnServer = new MenuButton("Serwer");
			btnServer.setOnMouseClicked(event -> {
				
				//this.getScene().getWindow().hide();
						
			    BreakoutApp gameApp = new BreakoutApp(true, (Stage)this.getScene().getWindow());

			    try 
			    {
					gameApp.run();
				}
			    catch (Exception e) 
			    {
					e.printStackTrace();
				}
				
			});
			
			MenuButton btnClient = new MenuButton("Klient");
			btnClient.setOnMouseClicked(event -> {
				
				this.getScene().getWindow().hide();
				
			    BreakoutApp gameApp = new BreakoutApp(false, (Stage)this.getScene().getWindow());
			    try 
			    {
					gameApp.run();
				}
			    catch (Exception e) 
			    {
					e.printStackTrace();
				}
				
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
 
			MenuButton btnBackFromOptions = new MenuButton("Back");
			btnBackFromOptions.setOnMouseClicked(event -> {
 
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
			menu1.getChildren().addAll(btnBackFromOptions, btnSound, btnVideo);
			menu2.getChildren().addAll(btnBackFromStart, btnServer, btnClient);
 
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
			text.getFont();
			text.setFont(Font.font(20));
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
	
	@Override
	public void run() 
	{
		launch();
	}
 
}
 