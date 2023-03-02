package app.allever.android.sample.cleaner.function;

import android.graphics.drawable.Drawable;

public class ProcessInfo {

    public String itemName;
    public Drawable itemIcon;
    public long itemSize;
    public boolean isChecked;
    public boolean isSystem;
    public String packageName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Drawable getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(Drawable itemIcon) {
        this.itemIcon = itemIcon;
    }

    public long getItemSize() {
        return itemSize;
    }

    public void setItemSize(long itemSize) {
        this.itemSize = itemSize;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
