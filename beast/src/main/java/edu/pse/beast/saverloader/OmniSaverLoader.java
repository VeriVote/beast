package edu.pse.beast.saverloader;

//prototype for saving and creating a class from a save file. the loading isn't implemented yet

//WARNING: it uses reflection, so maybe don't use for saving, but for inspiration

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import edu.pse.beast.toolbox.ErrorLogger;

public class OmniSaverLoader {

    private final static String headTag = "||HEAD||";
    private final static String tagLeft = "<";
    private final static String tagRight = ">";
    private final static String tagEnder = "/";
    private final static String dataIdentfier = "@";
    private final static String typeIdentfier = "$";
    private final static String containerTypeIdentifier = "§";

    public static List<String> createSaveFormat(Object obj) {

        ArrayList<String> format = new ArrayList<>();

        if (obj == null) {
            ErrorLogger.log("The given object was empty!");
            return null;
        } else {
            saveType(format, obj, "");
        }
        return format;
    }

    private static String getSaveableType(Object obj) {
        if (obj == null) {
            return "NULL";
        }
        if ((Object) obj instanceof Boolean) {
            return "BO";
        }
        if ((Object) obj instanceof Character) {
            return "C";
        }
        if ((Object) obj instanceof Byte) {
            return "BY";
        }
        if ((Object) obj instanceof Short) {
            return "SH";
        }
        if ((Object) obj instanceof Integer) {
            return "I";
        }
        if ((Object) obj instanceof Long) {
            return "L";
        }
        if ((Object) obj instanceof Float) {
            return "F";
        }
        if ((Object) obj instanceof Double) {
            return "D";
        }
        if ((Object) obj instanceof String) {
            return "S";
        }
        if ((Object) obj instanceof List || (Object) obj instanceof ArrayList || (Object) obj instanceof LinkedList) {
            return "LI";
        }
        if (obj.getClass().getName().contains("[")) {
            return "A";
        }
        // if there is no match, return null
        return null;
    }

    private static void savePrimitive(List<String> toAddTo, Object toSave, String varName, String type) {
        toAddTo.add(tagLeft + varName + typeIdentfier + type + typeIdentfier + tagRight);
        toAddTo.add(dataIdentfier + sanitize(toSave.toString()));
        toAddTo.add(tagLeft + tagEnder + varName + typeIdentfier + type + typeIdentfier + tagRight);
    }

    private static String sanitize(String toSanitize) {
        return toSanitize.replace("\\", "\\\\").replaceAll("\n", "\\n");
    }

    private static void saveNull(List<String> toAddTo, String varName) {
        toAddTo.add(tagLeft + varName + typeIdentfier + "NULL" + typeIdentfier + tagRight);
        toAddTo.add(tagLeft + tagEnder + varName + typeIdentfier + "NULL" + typeIdentfier + tagRight);
    }

    private static void saveList(List<String> toAddTo, List<?> toSave, String dataType, String varName, String type) {

        if (dataType.equals("")) {
            ErrorLogger.log(
                    "You just tried to save an unsupported multidimensional list. Good luck implementing it, you need it!");
        }

        // startTag
        if (toSave == null) {
            saveNull(toAddTo, varName);
            return;
        }

        toAddTo.add(tagLeft + varName + typeIdentfier + type + typeIdentfier + containerTypeIdentifier + dataType
                + containerTypeIdentifier + tagRight);

        if (toSave.size() == 0) {
            // save nothing except for the tags
        } else {
            for (int i = 0; i < toSave.size(); i++) {
                Object subObj = toSave.get(i);
                saveType(toAddTo, subObj, "");
            }
        }
        // endTag
        toAddTo.add(tagLeft + tagEnder + varName + typeIdentfier + type + typeIdentfier + containerTypeIdentifier
                + dataType + containerTypeIdentifier + tagRight);
    }

    private static void saveArray(List<String> toAddTo, Object toSave, String dataType, String varName, String type) {

        if (toSave == null) {
            saveNull(toAddTo, varName);
            return;
        }

        // startTag
        toAddTo.add(tagLeft + varName + typeIdentfier + type + typeIdentfier + containerTypeIdentifier + dataType
                + containerTypeIdentifier + tagRight);

        if (toSave instanceof Object[]) {
            for (int i = 0; i < ((Object[]) toSave).length; i++) {
                if ((((Object[]) toSave)[i]) == null) {
                    saveNull(toAddTo, "");
                } else {
                    saveType(toAddTo, ((Object[]) toSave)[i], "");
                }
            }
            // if it isn't an object, we now have to check which primitive type
            // it is
        } else if (toSave instanceof byte[]) {
            for (int i = 0; i < ((byte[]) toSave).length; i++) {
                saveType(toAddTo, ((byte[]) toSave)[i], "");
            }
        } else if (toSave instanceof short[]) {
            for (int i = 0; i < ((short[]) toSave).length; i++) {
                saveType(toAddTo, ((short[]) toSave)[i], "");
            }
        } else if (toSave instanceof int[]) {
            for (int i = 0; i < ((int[]) toSave).length; i++) {
                saveType(toAddTo, ((int[]) toSave)[i], "");
            }
        } else if (toSave instanceof long[]) {
            for (int i = 0; i < ((long[]) toSave).length; i++) {
                saveType(toAddTo, ((long[]) toSave)[i], "");
            }
        } else if (toSave instanceof float[]) {
            for (int i = 0; i < ((float[]) toSave).length; i++) {
                saveType(toAddTo, ((float[]) toSave)[i], "");
            }
        } else if (toSave instanceof double[]) {
            for (int i = 0; i < ((double[]) toSave).length; i++) {
                saveType(toAddTo, ((double[]) toSave)[i], "");
            }
        } else if (toSave instanceof boolean[]) {
            for (int i = 0; i < ((boolean[]) toSave).length; i++) {
                saveType(toAddTo, ((boolean[]) toSave)[i], "");
            }
        } else if (toSave instanceof char[]) {
            for (int i = 0; i < ((char[]) toSave).length; i++) {
                saveType(toAddTo, ((char[]) toSave)[i], "");
            }
        } else {
            System.err.println("Should never be reached. Some arraytype is missing!!");
        }

        // endTag
        toAddTo.add(tagLeft + tagEnder + varName + typeIdentfier + type + typeIdentfier + tagRight);

    }

    private static void saveComplexType(List<String> toAddTo, Object toSave, String varName, String type) {

        if (toSave == null) {
            saveNull(toAddTo, varName);
            return;
        }

        // startTag
        toAddTo.add(tagLeft + varName + typeIdentfier + type + typeIdentfier + tagRight);

        Field[] allVars = toSave.getClass().getDeclaredFields();

        // the fields for this class
        for (int i = 0; i < allVars.length; i++) {

            allVars[i].setAccessible(true);
            try {

                Object subObject = (allVars[i].get(toSave));

                saveType(toAddTo, subObject, allVars[i]);

            } catch (IllegalArgumentException e) {
                allVars[i].setAccessible(false);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                allVars[i].setAccessible(false);
                e.printStackTrace();
            }
            allVars[i].setAccessible(false);
        }

        // all fields from parent classes, that are lower than class
        Class<?> parent = toSave.getClass().getSuperclass();

        String superName = varName;

        while (parent.getSuperclass() != null && parent.getSuperclass() != java.lang.Class.class) {
            superName = superName + "." + parent.getName();
            Field[] superFields = parent.getDeclaredFields();

            for (int i = 0; i < superFields.length; i++) {
                superFields[i].setAccessible(true);
                String name = superName + "." + superFields[i].getName();

                try {
                    saveType(toAddTo, superFields[i].get(toSave), superFields[i]);
                } catch (IllegalArgumentException | IllegalAccessException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                superFields[i].setAccessible(false);
            }
            parent = parent.getSuperclass();
        }

        // endTag
        toAddTo.add(tagLeft + tagEnder + varName + typeIdentfier + type + typeIdentfier + tagRight);
    }

    private static void saveType(List<String> toAddTo, Object toSave, Field field) {

        String varName = "";
        if (field != null) {
            varName = field.getName();
        }
        String subType = getSaveableType(toSave);

        if (subType == null) {
            // it is a complex object
            saveComplexType(toAddTo, toSave, varName, toSave.getClass().getName());
            return;
        } else if (subType.equals("NULL")) {
            saveNull(toAddTo, varName);
            return;
        } else if (!subType.equals("A") && !subType.equals("LI")) {
            // it is a primitive object
            savePrimitive(toAddTo, toSave, varName, subType);
            return;
        } else if (subType.equals("A")) {
            saveArray(toAddTo, toSave, field.getGenericType().toString(), varName, subType);
            return;
        } else if (subType.equals("LI")) {
            saveList(toAddTo, (List<?>) toSave, field.getGenericType().toString(), varName, subType);
            return;
        }

        System.err.println("Should never be reached! ");
    }

    private static void saveType(List<String> toAddTo, Object toSave, String varName) {

        String subType = getSaveableType(toSave);

        if (subType == null) {
            // it is a complex object
            saveComplexType(toAddTo, toSave, varName, toSave.getClass().getName());
            return;
        } else if (subType.equals("NULL")) {
            saveNull(toAddTo, varName);
            return;
        } else if (!subType.equals("A") && !subType.equals("LI")) {
            // it is a primitive object
            savePrimitive(toAddTo, toSave, varName, subType);
            return;
        } else if (subType.equals("A")) {
            saveArray(toAddTo, toSave, "", varName, subType);
            return;
        } else if (subType.equals("LI")) {
            saveList(toAddTo, (List<?>) toSave, "", varName, subType);
            return;
        }

        System.err.println("Should never be reached! ");
    }

    public static Object createFromSaveFormat(List<String> format) {
        if (!checkFormat(format)) {
            System.err.println("Format is in the wrong format and can't be read!");
            return null;
        }

        return null;
    }

    private static Object createType(List<String> toLoad, int startIndex, int endIndex, boolean valid) {
        if (!valid) {
            return null;
        } else {
            String type = determineLoadType(toLoad, startIndex, valid);
            Object toReturn = null;

            switch (type) {
            case "p":
                toReturn = createPrimitive(toLoad, startIndex, valid);
                break;
            case "LI":
                toReturn = createList(toLoad, startIndex, getEndIndexForTag(toLoad, startIndex, valid), valid);
                break;
            case "A":
                toReturn = createArray(toLoad, startIndex, getEndIndexForTag(toLoad, startIndex, valid), valid);
                break;
            default:
                toReturn = createComplexObject(toLoad, startIndex, getEndIndexForTag(toLoad, startIndex, valid), valid);
                break;
            }

            if (!valid) {
                return null;
            } else {
                return toReturn;
            }
        }
    }

    private static Object createComplexObject(List<String> toLoad, int startIndex, int endIndex, boolean valid) {
        return null;
    }

    private static Object createPrimitive(List<String> toLoad, int startIndex, boolean valid) {
        if (!valid) {
            return null;
        }

        // primitive types have just the two tags and the value in between.
        if (toLoad.get(startIndex).split(typeIdentfier).length != 3) {
            valid = false;
            return null;
        }
        // the value has to start with an @
        if (!toLoad.get(startIndex + 1).startsWith(dataIdentfier)) {
            valid = false;
            return null;
        } else if (toLoad.get(startIndex + 1).length() < 2) {
            valid = false;
            return null;
        }

        String type = toLoad.get(startIndex).split(typeIdentfier)[1];

        String value = toLoad.get(startIndex + 1).replace(dataIdentfier, " ");

        Object primitiveType = null;

        try {
            switch (type) {
            case "NULL":
                primitiveType = null;
                break;
            case "BO":
                primitiveType = Boolean.parseBoolean(value);
                break;
            case "C":
                primitiveType = value.charAt(0);
                break;
            case "BY":
                primitiveType = Byte.parseByte(value);
                break;
            case "SH":
                primitiveType = Short.parseShort(value);
                break;
            case "I":
                primitiveType = Integer.parseInt(value);
                break;
            case "L":
                primitiveType = Long.parseLong(value);
                break;
            case "F":
                primitiveType = Float.parseFloat(value);
                break;
            case "D":
                primitiveType = Double.parseDouble(value);
                break;
            case "S":
                primitiveType = value;
                break;
            default:
                ErrorLogger.log("Du hast den primitiven typen: " + type + " vergessen");
                valid = false;
                break;
            }
        } catch (Exception e) {
            ErrorLogger.log("Parsing failed");
            valid = false;
        }
        return primitiveType;
    }

    private static Object createArray(List<String> toLoad, int startIndex, int endIndex, boolean valid) {
        if (!valid) {
            return null;
        }
        return null;
    }

    private static List<?> createList(List<String> toLoad, int startIndex, int endIndex, boolean valid) {
        if (!valid) {
            return null;
        }
        
        List<Object> list = new ArrayList<Object>();
        
        return null;
    }

    private static boolean checkFormat(List<String> format) {
        if (format.size() < 2) {
            /**
             * there are not at least 1 tag of each
             */
            return false;
        }

        Stack<String> tags = new Stack<String>();

        tags.push(headTag);

        for (Iterator<String> iterator = format.iterator(); iterator.hasNext();) {
            String line = (String) iterator.next();

            // no text without tags. if only head is there, a tag hast to follow
            if (tags.size() == 1) {
                if (!(line.startsWith(tagLeft))) {
                    return false;
                }
            }

            // search for tags
            if (line.startsWith(tagLeft)) {
                line = line.replace(tagLeft, "").replace(tagRight, "");
                if (line.contains(tagEnder)) {
                    if (!((tagEnder + tags.pop()).equals(line))) {
                        return false;
                    }
                } else {
                    tags.push(line);
                }
            }
            for (int i = 0; i < tags.size(); i++) {
                // System.out.println(tags.get(i));
            }
            // System.out.println("___________________");

        }

        if (tags.size() == 1) {
            return tags.pop().equals(headTag);
        } else {
            return false;
        }
    }

    /**
     * finds the position of the end tag that belongs to this start tag
     * 
     * @param toSave
     *            the list with the input
     * @param startIndex
     *            the index of this startta
     * @param valid
     *            a boolean that, if it is set to false, invalidates the whole
     *            loaded object
     * @return
     */
    private static int getEndIndexForTag(List<String> toLoad, int startIndex, boolean valid) {
        if (!valid) {
            return -1;
        }

        int endIndex = -1;
        // if it seems like a valid start tag
        if (toLoad.get(startIndex).startsWith(tagLeft) && !toLoad.get(startIndex).contains(tagEnder)) {
            for (int i = startIndex + 1; i < toLoad.size(); i++) {
                // if the new found line is an end tag
                if (toLoad.get(i).startsWith(tagLeft + tagEnder)) {
                    if (toLoad.get(startIndex).equals(toLoad.get(i).replace(tagEnder, ""))) {
                        // we found the end tag
                        endIndex = i;
                    }
                }
            }
        }
        if (endIndex == -1) {
            valid = false;
        }
        return endIndex;
    }

    private static String determineLoadType(List<String> toLoad, int index, boolean valid) {
        if (!valid) {
            return null;
        }

        if (toLoad.get(index).split(typeIdentfier).length != 3) {
            valid = false;
            return null;
        } else {
            String type = toLoad.get(index).split(typeIdentfier)[1];

            switch (type) {
            case "NULL":
                return "p";

            case "BO":
                return "p";

            case "C":
                return "p";

            case "BY":
                return "p";

            case "SH":
                return "p";

            case "I":
                return "p";

            case "L":
                return "p";

            case "F":
                return "p";

            case "D":
                return "p";

            case "S":
                return "p";

            default:
                return type;
            }
        }
    }
}