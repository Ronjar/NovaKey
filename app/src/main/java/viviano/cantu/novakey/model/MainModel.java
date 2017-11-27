package viviano.cantu.novakey.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import java.util.ArrayList;
import java.util.List;

import viviano.cantu.novakey.controller.BasicCorrections;
import viviano.cantu.novakey.controller.Controller;
import viviano.cantu.novakey.controller.Corrections;
import viviano.cantu.novakey.elements.Element;
import viviano.cantu.novakey.elements.MainElement;
import viviano.cantu.novakey.elements.keyboards.overlays.OverlayElement;
import viviano.cantu.novakey.elements.keyboards.Keyboard;
import viviano.cantu.novakey.elements.keyboards.Keyboards;
import viviano.cantu.novakey.model.loaders.ElementsLoader;
import viviano.cantu.novakey.model.loaders.KeyboardsLoader;
import viviano.cantu.novakey.model.loaders.Loader;
import viviano.cantu.novakey.model.loaders.MainDimensionsLoader;
import viviano.cantu.novakey.model.loaders.ThemeLoader;
import viviano.cantu.novakey.view.themes.MasterTheme;

/**
 * Created by Viviano on 6/10/2016.
 *
 * Model that stores all of it's data internally.
 * Upon creation it will load all of its data from the TrueModel,
 * which gets its data from user preferences.
 *
 */
public class MainModel implements Model {
    //Loaders
    private final Loader<List<Element>> mElementLoader;
    private final Loader<MainDimensions> mMainDimensionsLoader;
    private final Loader<MasterTheme> mThemeLoader;
    private final Loader<Keyboards> mKeyboardsLoader;

    private MainDimensions mDimensions;

    //Theme
    private MasterTheme mTheme;

    //States
    private ShiftState mShiftState;
    private int mCursorMode = 0;
    private InputState mInputState;

    //Keyboard
    private int mKeyboardCode = Keyboards.DEFAULT;
    private Keyboards mKeyboards;

    //Elements
    private MainElement mMain;
    private final List<Element> mElements;


    public MainModel(Context context) {
        //loaders
        mThemeLoader = new ThemeLoader(context);
        mMainDimensionsLoader = new MainDimensionsLoader(context);
        mElementLoader = new ElementsLoader();
        mKeyboardsLoader = new KeyboardsLoader(context);

        syncWithPrefs();

        //Input State determined during start
        mInputState = new InputState();
        Corrections corrections = new BasicCorrections();
        corrections.initialize(context);
        mInputState.setCorrections(corrections);

        mShiftState = ShiftState.UPPERCASE;


        mMain = new MainElement(getKeyboard());
        mElements = new ArrayList<>();
        List<Element> btns = mElementLoader.load();
        for (Element b : btns) {
            mElements.add(b);
        }
    }

    /**
     * Syncs the models with the user preferences
     *
     */
    @Override
    public void syncWithPrefs() {
        mDimensions = mMainDimensionsLoader.load();
        mTheme = mThemeLoader.load();
        mKeyboards = mKeyboardsLoader.load();
    }

    @Override
    public List<Element> getElements() {
        List<Element> list = new ArrayList<>(mElements);
        list.add(0, mMain);//first element
        return list;
    }


    @Override
    public void setOverlayElement(OverlayElement element) {
        mMain.setOverlay(element);
    }

    @Override
    public MainDimensions getMainDimensions() {
        return mDimensions;
    }

    @Override
    public MasterTheme getTheme() {
        return mTheme;
    }

    @Override
    public void setTheme(MasterTheme theme) {
        mTheme = theme;
    }

    /**
     * @return the current input state
     */
    @Override
    public InputState getInputState() {
        return mInputState;
    }

    /**
     * Uses the given editor info to update the input state
     *
     * @param editorInfo info used to generate input state
     * @param inputConnection connection used to input
     */
    @Override
    public void onStart(EditorInfo editorInfo, InputConnection inputConnection) {
        mInputState.updateConnection(editorInfo, inputConnection);
        syncWithPrefs();

        //reads theme from preferences & colors according to the app
        if (Settings.autoColor)
            mTheme.setPackage(editorInfo.packageName);

        switch (mInputState.getType()) {
            default:
            case TEXT:
                setKeyboard(Keyboards.DEFAULT);
                break;
            case NUMBER:
                setKeyboard(Keyboards.PUNCTUATION);
                break;
            case PHONE:
                setKeyboard(Keyboards.PUNCTUATION);
                break;
            case DATETIME:
                setKeyboard(Keyboards.PUNCTUATION);
                break;
        }

        //TODO: update shiftstate
    }

    /**
     * @return the key layout that should be drawn
     */
    @Override
    public Keyboard getKeyboard() {
        return mKeyboards.get(getKeyboardCode());
    }

    /**
     * @return the code/index of the current keyboard
     */
    @Override
    public int getKeyboardCode() {
        return mKeyboardCode;
    }

    /**
     * @param code key layout code
     */
    @Override
    public void setKeyboard(int code) {
        mKeyboardCode = code;
        setOverlayElement(getKeyboard());
    }

    /**
     * @return the current shift state of the keyboard
     */
    @Override
    public ShiftState getShiftState() {
        return mShiftState;
    }

    /**
     * @param shiftState the shiftState to set the keyboard to
     */
    @Override
    public void setShiftState(ShiftState shiftState) {
        this.mShiftState = shiftState;
    }

    /**
     * if cursor mode is 0 both the left and the right are moving,
     * if cursor mode is -1 the left only is moving,
     * if cursor mdoe is 1 the right only is moving
     *
     * @return current cursor mode
     */
    @Override
    public int getCursorMode() {
        return mCursorMode;
    }

    /**
     * if cursor mode is 0 both the left and the right are moving,
     * if cursor mode is -1 the left only is moving,
     * if cursor mdoe is 1 the right only is moving
     *
     * @param cursorMode cursor mode to set
     * @throws IllegalArgumentException if the param is outside the range [-1, 1]
     */
    @Override
    public void setCursorMode(int cursorMode) {
        if (cursorMode < -1 || cursorMode > 1)
            throw new IllegalArgumentException(cursorMode + " is outside the range [-1, 1]");
        mCursorMode = cursorMode;
    }
}
