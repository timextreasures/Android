/*
 * Copyright (c) 2017 DuckDuckGo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.duckduckgo.app.ui.tabswitcher;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.duckduckgo.app.R;
import com.duckduckgo.app.ui.tab.TabEntity;
import com.duckduckgo.app.util.UrlUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabViewHolder extends RecyclerView.ViewHolder {

    interface OnTabListener {
        void onTabSelected(View v, int position);

        void onTabDeleted(View v, int position);
    }

    @BindView(R.id.tab_favicon_image_view)
    ImageView faviconImageView;

    @BindView(R.id.tab_title_text_view)
    TextView titleTextView;

    @BindView(R.id.tab_url_text_view)
    TextView urlTextView;

    @BindView(R.id.tab_delete_image_button)
    ImageButton deleteImageButton;

    public TabViewHolder(View itemView, final OnTabListener onTabListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTabListener.onTabSelected(v, getAdapterPosition());
            }
        });
        deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTabListener.onTabDeleted(v, getAdapterPosition());
            }
        });
        Typeface font = Typeface.createFromAsset(itemView.getContext().getAssets(), "fonts/proximanova-semibold.otf");
        titleTextView.setTypeface(font);
        urlTextView.setTypeface(font);
    }

    public void setTab(TabEntity tabEntity) {
        titleTextView.setText(tabEntity.getTitle());
        String urlLabel = UrlUtils.getUrlWithoutScheme(tabEntity.getCurrentUrl());
        urlTextView.setText(urlLabel);

        if (tabEntity.getFavicon() != null) {
            faviconImageView.setImageBitmap(tabEntity.getFavicon());
        } else {
            faviconImageView.setImageResource(R.drawable.ic_globe);
        }
    }

    public static TabViewHolder inflate(ViewGroup parent, OnTabListener onTabListener) {
        return new TabViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.viewholder_tab, parent, false),
                onTabListener);
    }
}
