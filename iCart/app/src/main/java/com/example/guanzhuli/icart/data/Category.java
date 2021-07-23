package com.example.guanzhuli.icart.data;

/**
 * Created by Xu on 12/31/2016.
 */
public class Category {
    private String mImageUrl;
    private String mName;
    private Integer mId;
    private String mDescription;

    public Category(String imageUrl, String name, Integer id, String description) {
        mImageUrl = imageUrl;
        mName = name;
        mId = id;
        mDescription = description;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getName() {
        return mName;
    }

    public Integer getId() {
        return mId;
    }

    public String getDescription() {
        return mDescription;
    }
}
