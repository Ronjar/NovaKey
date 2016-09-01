package viviano.cantu.novakey.tests;

import android.app.Activity;
import android.os.Bundle;

import viviano.cantu.novakey.R;
import viviano.cantu.novakey.view.drawing.drawables.Drawable;
import viviano.cantu.novakey.view.drawing.emoji.Emoji;
import viviano.cantu.novakey.view.drawing.emoji.ThrowAwayView;
import viviano.cantu.novakey.HexGridView;

public class EmojiSettingActivity extends Activity {

    int x = 0, y = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Emoji.load(this);
        setContentView(R.layout.activity_emoji_setting);


        final Drawable[][] grid = new Drawable[10][10];

        final HexGridView hex = (HexGridView)findViewById(R.id.hex);
        hex.setGrid(grid);

        ThrowAwayView tav = (ThrowAwayView)findViewById(R.id.throaway);
        tav.setListener(new ThrowAwayView.onClickListener() {
            @Override
            public void onClik(Emoji e) {
                grid[x][y] = e;
                hex.setGrid(grid);
                hex.invalidate();

                x++;
                if (x >= 10) {
                    x = 0;
                    y++;
                }
                if (y >= 10)
                    y = 0;
            }
        });
    }
}
