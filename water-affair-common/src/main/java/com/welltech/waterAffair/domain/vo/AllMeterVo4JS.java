package com.welltech.waterAffair.domain.vo;

/**
 * Created by lenovo on 2016/12/26.
 */
public class AllMeterVo4JS {
    private String text;
    private String id;
    private TreeState state;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TreeState getState() {
        return state;
    }

    public void setState(TreeState state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public class TreeState{
        private boolean disabled;
        private boolean selected;

        public TreeState(boolean disabled,boolean selected){
            this.disabled=disabled;
            this.selected=selected;
        }

        public boolean isDisabled() {
            return disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

}

