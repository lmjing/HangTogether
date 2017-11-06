package com.example.woojinroom.hangto.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.woojinroom.hangto.R;

/**
 * Created by kksd0900 on 16. 10. 11..
 */
public class ViewHolderMessage extends ViewHolderParent {
    public ImageView imageView;
    public TextView textId;
    public TextView textContent;
    public TextView textTime;
    public TextView textNew;
    public TextView textDocument;
    public TextView textMessageID;
    public TextView textMessageTime;

    public ViewHolderMessage(View v) {
        super(v);
        imageView =(ImageView)v.findViewById(R.id.imageView);
        textId = (TextView)v.findViewById(R.id.textView);
        textContent = (TextView)v.findViewById(R.id.textView2);
        textTime = (TextView)v.findViewById(R.id.textView3);
        textNew = (TextView) v.findViewById(R.id.text_new);
        textDocument = (TextView)v.findViewById(R.id.text_document);
        textMessageID = (TextView)v.findViewById(R.id.textView34);
        textMessageTime = (TextView)v.findViewById(R.id.textView58);

    }
}