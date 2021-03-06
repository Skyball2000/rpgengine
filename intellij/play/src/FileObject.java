
public class FileObject extends Entity {
    private byte[] fileData = null;

    public FileObject() {
        name = "New File";
        description = "Description";
    }

    public FileObject(String name, String desc) {
        this.name = name;
        description = desc;
    }

    public FileObject(String name, byte array[]) {
        this.name = name;
        description = "A " + name.replaceAll("[^\\.]+\\.(.+)", "$1") + " file";
        fileData = array;
    }

    public FileObject(String[] fileInput, byte array[]) {
        try {
            fileData = array;
            name = fileInput[0];
            description = fileInput[1];
            uid = fileInput[2];
            for (int i = 3; i < fileInput.length; i++) {
                if (fileInput[i].contains("++ev++")) {
                    fileInput[i] = fileInput[i].replace("++ev++", "");
                    eventName.add(fileInput[i].split("---")[0]);
                    eventCode.add(fileInput[i].split("---")[1]);
                } else if (fileInput[i].contains("++tag++")) {
                    tags.add(fileInput[i].replace("++tag++", ""));
                } else if (fileInput[i].contains("++variable++")) {
                    fileInput[i] = fileInput[i].replace("++variable++", "");
                    localVarUids.add(fileInput[i].split("---")[0]);
                    localVarName.add(fileInput[i].split("---")[1]);
                    localVarType.add(fileInput[i].split("---")[2]);
                    localVarValue.add(fileInput[i].split("---")[3]);
                }
            }
        } catch (Exception e) {
            StaticStuff.error("FileObject '" + name + "' contains invalid data.");
        }
    }

    public String generateSaveString() {
        StringBuilder str = new StringBuilder("" + name + "\n" + description + "\n" + uid + "\n");
        for (int i = 0; i < eventName.size(); i++)
            str.append("\n++ev++").append(eventName.get(i)).append("---").append(eventCode.get(i));
        for (String tag : tags) str.append("\n++tag++").append(tag);
        for (int i = 0; i < localVarName.size(); i++)
            str.append("\n++variable++").append(localVarUids.get(i)).append("---").append(localVarName.get(i)).append("---").append(localVarType.get(i)).append("---").append(localVarValue.get(i));
        return str.toString();
    }

    public void openFile() {
        FileManager.writeFileFromByteArray("res/tmp/" + name, fileData);
        FileManager.openFile("res/tmp/" + name);
    }

    public String[] toStringArray() {
        FileManager.writeFileFromByteArray("res/tmp/" + name, fileData);
        return FileManager.readFile("res/tmp/" + name);
    }

    public byte[] getByteArray() {
        return fileData;
    }
}
