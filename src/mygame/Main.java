package mygame;
import Local.Local;
import Multiplayer.Multiplayer;
import Multiplayer.GameGUI;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import viewmodel.MainMenu;
import com.jme3.system.AppSettings;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Monopoly");
        settings.setSettingsDialogImage("Materials/Images/Monopoly Interface.png");
        settings.setVSync(true);
        settings.setFullscreen(true);
        settings.setResolution(1920, 1080);
        settings.setGammaCorrection(true);
        Main app = new Main();
        app.setSettings(settings);
        app.setShowSettings(true);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setDragToRotate(true);
        setDisplayFps(false);
        setDisplayStatView(false);
        stateManager.attach(new MainMenu(this));
        //stateManager.attach(new Multiplayer(this));
        //stateManager.attach(new MainMenu(this));
        //stateManager.attach(new GameGUI());
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
