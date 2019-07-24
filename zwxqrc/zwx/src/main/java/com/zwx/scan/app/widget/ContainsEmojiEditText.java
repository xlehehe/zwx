package com.zwx.scan.app.widget;

import android.content.Context;
import android.graphics.Paint;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.EditText;

import com.zwx.library.utils.ToastUtils;

/**
 * author : lizhilong
 * time   : 2019/01/31
 * desc   :
 * version: 1.0
 **/
public class ContainsEmojiEditText extends android.support.v7.widget.AppCompatEditText {

    //输入表情前的光标位置
    private int cursorPos;
    //输入表情前EditText中的文本
    private String inputAfterText;
    //是否重置了EditText的内容
    private boolean resetText;

    private Context mContext;

    public ContainsEmojiEditText(Context context) {
        super(context);
        this.mContext = context;
        initEditText();
    }

    public ContainsEmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initEditText();
    }

    public ContainsEmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initEditText();
    }

    // 初始化edittext 控件
    private void initEditText() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
                if (!resetText) {
                    cursorPos = getSelectionEnd();
                    // 这里用s.toString()而不直接用s是因为如果用s，
                    // 那么，inputAfterText和s在内存中指向的是同一个地址，s改变了，
                    // inputAfterText也就改变了，那么表情过滤就失败了
                    inputAfterText= s.toString();
                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!resetText) {
                    if (count >= 2) {//表情符号的字符长度最小为2
                        CharSequence input = s.subSequence(cursorPos, cursorPos + count);
                        if (containsEmoji(input.toString())) {
                            resetText = true;
//                            ToastUtils.showShort("不支持输入Emoji表情符号");
                            //是表情符号就将文本还原为输入表情符号之前的内容
                            setText(inputAfterText);
                            CharSequence text = getText();
                            if (text instanceof Spannable) {
                                Spannable spanText = (Spannable) text;
                                Selection.setSelection(spanText, text.length());
                            }
                        }
                    }
                } else {
                    resetText = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }



    //随输入内容自动扩展


    // 最小字体
    private static final float DEFAULT_MIN_TEXT_SIZE = 8.0f;
    // 最大字体
    private static final float DEFAULT_MAX_TEXT_SIZE = 16.0f;

    private Paint textPaint;
    private float minTextSize = DEFAULT_MIN_TEXT_SIZE;
    private float maxTextSize = DEFAULT_MAX_TEXT_SIZE;



   /* private void initialise() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        if (this.textPaint == null) {
            this.textPaint = new Paint();
            this.textPaint.set(this.getPaint());
        }
        this.maxTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.maxTextSize, displayMetrics);
        if (DEFAULT_MIN_TEXT_SIZE >= maxTextSize) {
            this.maxTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.maxTextSize, displayMetrics);
        }
        this.maxTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.maxTextSize, displayMetrics);
        this.minTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.minTextSize, displayMetrics);
    }*/

    /**
     * Re size the font so the specified text fits in the text box * assuming
     * the text box is the specified width.
     */
   /* private void fitText(String text, int textWidth) {
        if (textWidth > 0) {
            // 单行可见文字宽度
            int availableWidth = textWidth - this.getPaddingLeft() - this.getPaddingRight();
            float trySize = maxTextSize;
            // 先用最大字体写字
            textPaint.setTextSize(trySize);
            // 如果最大字体>最小字体 && 最大字体画出字的宽度>单行可见文字宽度
            while ((trySize > minTextSize) && (textPaint.measureText(text) > availableWidth)) {
                // 最大字体小一号
                trySize -= 1;
                // 保证大于最小字体
                if (trySize <= minTextSize) {
                    trySize = minTextSize;
                    break;
                }
                // 再次用新字体写字
                textPaint.setTextSize(trySize);
            }
            this.setTextSize(trySize);
        }
    }*/


    /**
     * 重写setText
     * 每次setText的时候
     *
     * @param text
     * @param type
     */
  /*  @Override
    public void setText(CharSequence text, BufferType type) {
        this.initialise();
        String textString = text.toString();
        float trySize = maxTextSize;
        if (this.textPaint == null) {
            this.textPaint = new Paint();
            this.textPaint.set(this.getPaint());
        }
        this.textPaint.setTextSize(trySize);
        // 计算设置内容前 内容占据的宽度
        int textWidth = (int) this.textPaint.measureText(textString);
        // 拿到宽度和内容，进行调整
        this.fitText(textString, textWidth);
        super.setText(text, type);
    }


    @Override
    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        this.fitText(text.toString(), this.getWidth());
    }*/


    /**
     * This is called during layout when the size of this view has changed. If
     * you were just added to the view hierarchy, you're called with the old
     * values of 0.
     *
     * @param w    Current width of this view.
     * @param h    Current height of this view.
     * @param oldw Old width of this view.
     * @param oldh Old height of this view.
     */
  /*  @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // 如果当前view的宽度 != 原来view的宽度
        if (w != oldw) this.fitText(this.getText().toString(), w);
    }*/



}
