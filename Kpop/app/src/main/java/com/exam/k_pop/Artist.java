package com.exam.k_pop;

import java.util.Random;

public class Artist {
    private String group;
    private String name;
    private boolean init = false;
    private String[] namesImages;

    public Artist(String group, String name, String[] namesImages) {
        this.group = group;
        this.name = name;
        this.namesImages = namesImages; //массив имен всех фоток артиста
    }

    public String getNamesImages() {
        Random rand = new Random();

        return namesImages[rand.nextInt(namesImages.length)]; //возвращаем рандомную фотку Артиста
    }

    public String getName() {
        return name;
    }

        public String getGroup() {
            return group;
        }

    public void setInit(boolean init) {
        this.init = init;
    }

    public void Init() {
        this.init = true;
    }

    public String getFolder() {
        return group + "/" + name + "/" + getNamesImages();
    }

    public boolean isInit() {
        return init;
    }
}
