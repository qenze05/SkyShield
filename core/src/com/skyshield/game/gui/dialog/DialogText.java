package com.skyshield.game.gui.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.skyshield.game.screens.GameScreen;

public class DialogText extends Table {

    public static int textCounter = 1;
    final float letterSpawnTime = .02f;
    float timer = 0;

    String drawText;
    int stringIndex = 0;
    private BitmapFont font;
    private String text;
    private boolean written;
    public DialogText(String text) {
        this.text = text;
        this.drawText = "";
        setFont();
        this.written = false;
    }

    public boolean isWritten() {
        return this.written;
    }

    public void update(float delta) {
        timer += delta;
        if (timer >= letterSpawnTime) {
            if(stringIndex > 200 && stringIndex<text.length()) {
//                text = text.substring(drawText.length());
//                drawText = "";
//                stringIndex = 0;
                if(text.charAt(stringIndex) == '\n') drawText = drawText.substring(drawText.split("\\n")[0].length()+1) + text.charAt(stringIndex);
                else drawText = drawText + text.charAt(stringIndex);
                stringIndex++;
            }
            else if(stringIndex<text.length()) {
                drawText = drawText + text.charAt(stringIndex);
                stringIndex++;
            }else written = true;
            timer -= letterSpawnTime;
        }
        GameScreen.stage.getBatch().begin();
        font.draw(GameScreen.stage.getBatch(), drawText, 185, 630);
        GameScreen.stage.getBatch().end();
    }


    public void setFont() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("a_OldTyper.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.characters = "абвгґдеєжзиійїклмнопрстуфхцчшщьюяАВГГҐДЕЄЖЗИІЙЇКЛМНОПРСТУФХЦЧШЩЬЮЯ,.?«»’–-";
        fontParameter.size = 15;
        font = fontGenerator.generateFont(fontParameter);
        fontGenerator.dispose();
    }

    public void skip() {
        drawText = text.substring(text.length()-200);
        text = text.substring(text.length()-200);
        stringIndex = 200;
    };
    public static String getText() {
        switch (textCounter) {
            case 1 -> {
                return "Алло, алло, прийом. Фух, \n" +
                        "нарешті вийшло зв’язатися з \n" +
                        "тобою, салаго. На рації генерал \n" +
                        "Аскель, головнокомандувач  \n" +
                        "військових сил Етерії. \n"+
                        "Хоча, навіщо я це кажу, ти ж \n" +
                        "і так знаєш, як звуть начальство? \n" +
                        "Проїхали. В буремні часи живемо, \n" +
                        "салаго… Одначе, місія тобі \n" +
                        "довірена справді надважлива.\n" +
                        "Ти навіть не уявляєш яка важлива.\n" +
                        "Ми довго готувалися до цієї \n" +
                        "війни, проте нам і в голову \n" +
                        "не приходило, що тупоголові \n" +
                        "ординці навчаться використову-\n" +
                        "вати провідну повітряну зброю. \n" +
                        "Інформація про те, що ворог \n" +
                        "має на озброєнні залізних птахів, \n" +
                        "по простому ракети, надійшла від \n" +
                        "нашої розвідки лише тиждень \n" +
                        "тому. Ми і самі не можемо в \n" +
                        "це повірити. Ми вважали, що \n" +
                        "крізь нашу розвідку і муха не \n" +
                        "пролетить, але ці покидьки \n" +
                        "примудрилися домовитися з \n" +
                        "цивілізаціями сходу про постав-\n" +
                        "ки і тримали їх у великій \n" +
                        "таємниці. Акзилія погодилася \n" +
                        "надати нам певні елементи \n" +
                        "своєї системи «Небесний щит». \n" +
                        "Оскільки часу до вторгнення \n" +
                        "залишалося обмаль, ми \n" +
                        "тільки-но імпортували перші \n" +
                        "установки. Поки що тільки \n" +
                        "найпростіша зброя: «Крони» \n" +
                        "першого, другого та третього \n" +
                        "поколінь. Різняться базовими \n" +
                        "характеристиками та і тільки. \n" +
                        "Їх головнокомандувач надав \n" +
                        "певні інструкції з викори-\n" +
                        "стання, передаю їх тобі:\n" +
                        "Там нічого складного насправді.\n" +
                        "Оптимальна швидкість – це \n" +
                        "показник, який вказує \n" +
                        "максимальну швидкість \n" +
                        "повітряного об’єкта, \n" +
                        "на якій установка здатна збивати\n" +
                        "його з максимальною\n" +
                        "ефективністю. Якщо об’єкт \n" +
                        "летить швидше, ефективність \n" +
                        "падатиме. Оптимальний розмір – \n" +
                        "схожа механіка. Хіба що динаміка \n" +
                        "протилежна – менші об’єкти \n" +
                        "збивати тяжче.\n" +
                        "Перезарядка – час, необхідний \n" +
                        "на те, щоб установка знову \n" +
                        "увійшла в стрій після \n" +
                        "спроби збиття.\n" +
                        "Радіус – це відповідно радіус \n" +
                        "круга, який\n" +
                        "покривається установкою.\n" +
                        "Рівномірність – здатність \n" +
                        "зберігати центрову \n" +
                        "ефективність на краях \n" +
                        "діапазону дії.\n" +
                        "По суті ці п’ять характеристик \n" +
                        "визначають ефективність \n" +
                        "установки. Думаю, що ти \n" +
                        "розберешся з цими \n" +
                        "справами. Тобі доручено \n" +
                        "керувати системою, а отже \n" +
                        "розставити всі \n" +
                        "елементи у стратегічній \n" +
                        "відповідності, захистивши \n" +
                        "ключові об’єкти.\n" +
                        "Наразі наша розвідка доповідає, \n" +
                        "що наступні удари можуть прийтися \n" +
                        "по Сільвестрісу та \n" +
                        "Інтерфлюмінусу із району \n" +
                        "ворожого міста Зулим-Кала. \n" +
                        "Поки що на балансі не так багато \n" +
                        "валюти, тож встанови по установці \n" +
                        "першого рівня поблизу цих міст. \n" +
                        "Удачі.\n";
            }
            case 2 -> {
                return "ЮХУ! \n" +
                        "Обидві повітряні цілі збито. \n" +
                        "Молодець, салаго. \n" +
                        "Щоправда, ми маємо невелику \n" +
                        "проблемку – повідомляють про \n" +
                        "пуск ще однієї пташки в район \n" +
                        "гір, вірогідно, у місто Монфіор. \n" +
                        "Моє рідне, до речі. \n" +
                        "Хай вони тільки спробують.";
            }
            case 3 -> {
                return "ЧОРТ! Ціль досягла міста! \n" +
                        "О рідний мій Монфіор! \n" +
                        "Вони заплатять за це, чуєш! \n" +
                        "Будь проклятий Карам-Гулук!\n" +
                        "Салаго… \n" +
                        "Тобі доведеться трохи \n" +
                        "приглянути за містом. \n" +
                        "Обери його та оформи ремонт. \n" +
                        "Формула визначення ціни \n" +
                        "скоро стане очевидною. \n" +
                        "Удачі.\n";
            }
        }
        return "нема тексту капець";
    }
}
