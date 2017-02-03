package edu.pse.beast.saverloader;

//prototype for saving and creating a class from a save file. the loading isn't implemented yet
//WARNING: it uses reflection, so maybe don't use for saving, but for inspiration


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class OmniSaverLoader {

    private final static String headTag = "||HEAD||";
    private final static String tagLeft = "<";
    private final static String tagRight = ">";
    private final static String tagEnder = "/";
    private final static String dataIdentfier = "@";
    private final static String typeIdentfier = "$";

//    public static void main(String... args)
//            throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException {
//        
//
//        List<String> result = createSaveFormat(tester2);
//
//        
//        
//        for (Iterator<String> iterator = result.iterator(); iterator.hasNext();) {
//            String line = (String) iterator.next();
//            System.out.println(line);
//        }
//        
//        System.out.println("==========================");
//        
//        createFromSaveFormat(result);
//    }

    public static List<String> createSaveFormat(Object obj) {

        ArrayList<String> format = new ArrayList<>();

        if (obj == null) {
            System.err.println("ES WURDE EIN LEERES OBJEKT �BERGEBEN!");
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
            return "Sh";
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

    private static void saveList(List<String> toAddTo, List<?> toSave, String varName, String type) {
        // startTag
        if (toSave == null) {
            saveNull(toAddTo, varName);
            return;
        }

        toAddTo.add(tagLeft + varName + typeIdentfier + type + typeIdentfier + tagRight);

        if (toSave.size() == 0) {
            // save nothing except for the tags
        } else {
            for (int i = 0; i < toSave.size(); i++) {
                Object subObj = toSave.get(i);
                saveType(toAddTo, subObj, "");
            }
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

                saveType(toAddTo, subObject, allVars[i].getName());

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
                    saveType(toAddTo, superFields[i].get(toSave), name);
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

    private static void saveArray(List<String> toAddTo, Object toSave, String varName, String type) {

        if (toSave == null) {
            saveNull(toAddTo, varName);
            return;
        }

        // startTag
        toAddTo.add(tagLeft + varName + typeIdentfier + type + typeIdentfier + tagRight);

        if (toSave instanceof Object[]) {
            for (int i = 0; i < ((Object[]) toSave).length; i++) {
                if ((((Object[]) toSave)[i]) == null) {
                    saveNull(toAddTo, "");
                } else {
                    saveType(toAddTo, ((Object[]) toSave)[i], "");
                }
            }
            //if it isn't an object, we now have to check which primitive type it is
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
            savePrimitive(toAddTo, toSave, varName, subType);
            return;
        } else if (subType.equals("A")) {

            saveArray(toAddTo, toSave, varName, subType);
            return;
        } else if (subType.equals("LI")) {
            saveList(toAddTo, (List<?>) toSave, varName, subType);
            return;
        }

        System.err.println("Should never be reached! ");
    }
    
    
    private static Object createFromSaveFormat(List<String> format) {
        if (!checkFormat(format)) {
            System.err.println("Format is in the wrong format and can't be read!");
            return null;
        }
        
        return null;
    }
    
    
    
    
    
    
    
    
    private static boolean checkFormat(List<String> format) {
        if (format.size() < 2) {
            /**
             * there are not at leas 1 tag of each
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
           //     System.out.println(tags.get(i));
            }
            //System.out.println("___________________");
            
            
        }

        if (tags.size() == 1) {
            return tags.pop().equals(headTag);
        } else {
            return false;
        }
    }
    
}