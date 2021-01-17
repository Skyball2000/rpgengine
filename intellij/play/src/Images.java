
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Images extends JFrame {
    private static ArrayList<String> uids = new ArrayList<String>();
    private static ArrayList<String> filename = new ArrayList<String>();
    private ArrayList<Boolean> firstTimeOpen = new ArrayList<Boolean>();
    private static ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
    String namespace;

    public Images(String namespace) {
        this.namespace = namespace;
    }

    public Images(String namespace, String files[]) {
        uids.clear();
        filename.clear();
        firstTimeOpen.clear();
        images.clear();
        this.namespace = namespace;
        int lastImage = 0, imagesAmount = 0;
        try {
            for (int i = 0; i < files.length; i++) {
                if (files[i].contains("++image++")) {
                    lastImage = i;
                    files[i] = files[i].replace("++image++", "");
                    uids.add(files[i].split("---")[0]);
                    filename.add(files[i].split("---")[1]);
                    images.add(ImageIO.read(new File(Manager.extraFilePath + "adventures/" + namespace + "/images/" + uids.get(imagesAmount) + ".png")));
                    firstTimeOpen.add(false);
                    imagesAmount++;
                }
            }
        } catch (Exception e) {
            StaticStuff.error("Image '" + files[lastImage] + "' contains invalid data.<br>" + e);
        }
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String generateMenuString() {
        String str = uids.size() + " image(s):";
        for (int i = 0; i < uids.size(); i++)
            str = str + "\n" + filename.get(i) + "  " + uids.get(i);
        return str;
    }

    public void addImage(String name, String path) {
        if (name == null || path == null) return;
        if (name.equals("") || path.equals("")) return;
        try {
            this.images.add(ImageIO.read(new File(path)));
        } catch (Exception e) {
            StaticStuff.error("Unable to get image.");
        }
        filename.add(name);
        uids.add(UID.generateUID());
        firstTimeOpen.add(true);
    }

    public boolean deleteImage(String uid) {
        int index = uids.indexOf(uid);
        if (index == -1) return false;
        uids.remove(index);
        filename.remove(index);
        images.remove(index);
        firstTimeOpen.remove(index);
        return true;
    }

    public boolean openImage(String uid, String text, int size, boolean pauseProgram) {
        int index = uids.indexOf(uid);
        if (index == -1) return false;
        Log.add("Open image " + uid + " with text '" + text + "' and size " + size);
        PopupImage popImage = new PopupImage(text, images.get(index), size);
        new Thread() {
            public void run() {
                popImage.createComponents();
            }
        }.start();
        while (popImage.selected == -1 && pauseProgram) Sleep.milliseconds(100);
        return true;
    }

    public boolean imageExists(String uidName) {
        return (uids.contains(uidName) || filename.contains(uidName));
    }

    public String getImageName(String uidName) {
        if (StaticStuff.isValidUIDSilent(uidName))
            return filename.get(uids.indexOf(uidName));
        return filename.get(filename.indexOf(uidName));
    }

    public String getImageUID(String name) {
        return uids.get(filename.indexOf(name));
    }

    public static BufferedImage getBufferedImage(String uid) {
        return images.get(uids.indexOf(uid));
    }

    public static BufferedImage readImageFromFile(String filename) {
        try {
            return ImageIO.read(new File(filename));
        } catch (Exception e) {
            try {
                return ImageIO.read(new File("res/img/null.png"));
            } catch (Exception e2) {
                return null;
            }
        }
    }

    public static int getAmount() {
        return filename.size();
    }

    public static String getName(int index) {
        return filename.get(index);
    }

    public static String getUID(int index) {
        return uids.get(index);
    }

    public String[] getUIDs() {
        String ret[] = new String[uids.size()];
        for (int i = 0; i < uids.size(); i++) ret[i] = uids.get(i);
        return ret;
    }
}