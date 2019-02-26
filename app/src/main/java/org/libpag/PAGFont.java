package org.libpag;

import android.util.Xml;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class PAGFont {
    private static final String DefaultLanguage = "zh-Hans";
    private static final Pattern FILENAME_WHITESPACE_PATTERN = Pattern.compile("^[ \\n\\r\\t]+|[ \\n\\r\\t]+$");
    private static final String[] FallbackFontFileNames = new String[]{"/system/fonts/NotoSansCJK-Regular.ttc", "/system/fonts/NotoSansSC-Regular.otf", "/system/fonts/DroidSansFallback.ttf"};
    private static final String SystemFontConfigPath_JellyBean = "/system/etc/fallback_fonts.xml";
    private static final String SystemFontConfigPath_Lollipop = "/system/etc/fonts.xml";
    private static final String SystemFontPath = "/system/fonts/";
    private static boolean systemFontLoaded = false;
    public String fontFamily = "";
    public String fontStyle = "";

    private static class FontConfig {
        String fileName;
        String language;
        int ttcIndex;
        int weight;

        private FontConfig() {
            this.language = "";
            this.fileName = "";
            this.ttcIndex = 0;
            this.weight = 400;
        }
    }

    public static native PAGFont RegisterFont(String str, int i);

    private static native PAGFont RegisterFontBytes(byte[] bArr, int i, int i2);

    private static native void SetFallbackFontPaths(String[] strArr, int[] iArr);

    public static PAGFont RegisterFont(String str) {
        return RegisterFont(str, 0);
    }

    public static PAGFont RegisterFont(InputStream inputStream) {
        return RegisterFont(inputStream, 0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x002a  */
    public static PAGFont RegisterFont(InputStream r6, int r7) {
        /*
        r0 = 0;
        r1 = new byte[r0];
        r2 = r0;
        r0 = r1;
    L_0x0005:
        r1 = r6.available();	 Catch:{ IOException -> 0x0023 }
        if (r1 <= 0) goto L_0x0027;
    L_0x000b:
        r1 = r6.available();	 Catch:{ IOException -> 0x0023 }
        r1 = r1 + r2;
        r1 = new byte[r1];	 Catch:{ IOException -> 0x0023 }
        r3 = 0;
        r4 = 0;
        java.lang.System.arraycopy(r0, r3, r1, r4, r2);	 Catch:{ IOException -> 0x0031 }
        r0 = r6.available();	 Catch:{ IOException -> 0x0031 }
        r0 = r6.read(r1, r2, r0);	 Catch:{ IOException -> 0x0031 }
        r0 = r0 + r2;
        r2 = r0;
        r0 = r1;
        goto L_0x0005;
    L_0x0023:
        r1 = move-exception;
    L_0x0024:
        com.google.c.a.a.a.a.a.e(r1);
    L_0x0027:
        r1 = r0.length;
        if (r1 != 0) goto L_0x002c;
    L_0x002a:
        r0 = 0;
    L_0x002b:
        return r0;
    L_0x002c:
        r0 = RegisterFontBytes(r0, r2, r7);
        goto L_0x002b;
    L_0x0031:
        r0 = move-exception;
        r5 = r0;
        r0 = r1;
        r1 = r5;
        goto L_0x0024;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.libpag.PAGFont.RegisterFont(java.io.InputStream, int):org.libpag.PAGFont");
    }

    static {
        System.loadLibrary("libpag");
        loadSystemFonts();
    }

    private static FontConfig[] parseLollipop() throws XmlPullParserException, IOException {
        FontConfig[] fontConfigArr = new FontConfig[0];
        try {
            InputStream fileInputStream = new FileInputStream(SystemFontConfigPath_Lollipop);
            try {
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setInput(fileInputStream, null);
                newPullParser.nextTag();
                fontConfigArr = readFamilies(newPullParser);
                return fontConfigArr;
            } finally {
                fileInputStream.close();
            }
        } catch (IOException e) {
        }
        return fontConfigArr;
    }

    private static FontConfig[] readFamilies(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        xmlPullParser.require(2, null, "familyset");
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("family")) {
                    readFamily(xmlPullParser, arrayList);
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        FontConfig[] fontConfigArr = new FontConfig[arrayList.size()];
        arrayList.toArray(fontConfigArr);
        return fontConfigArr;
    }

    private static void readFamily(XmlPullParser xmlPullParser, ArrayList<FontConfig> arrayList) throws XmlPullParserException, IOException {
        xmlPullParser.getAttributeValue(null, "name");
        String attributeValue = xmlPullParser.getAttributeValue(null, "lang");
        ArrayList arrayList2 = new ArrayList();
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("font")) {
                    arrayList2.add(readFont(xmlPullParser));
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        if (!arrayList2.isEmpty()) {
            FontConfig fontConfig;
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                fontConfig = (FontConfig) it.next();
                if (fontConfig.weight == 400) {
                    break;
                }
            }
            fontConfig = null;
            if (fontConfig == null) {
                fontConfig = (FontConfig) arrayList2.get(0);
            }
            if (!fontConfig.fileName.isEmpty()) {
                if (attributeValue == null) {
                    attributeValue = "";
                }
                fontConfig.language = attributeValue;
                arrayList.add(fontConfig);
            }
        }
    }

    private static FontConfig readFont(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        FontConfig fontConfig = new FontConfig();
        String attributeValue = xmlPullParser.getAttributeValue(null, "index");
        fontConfig.ttcIndex = attributeValue == null ? 0 : Integer.parseInt(attributeValue);
        attributeValue = xmlPullParser.getAttributeValue(null, "weight");
        fontConfig.weight = attributeValue == null ? 400 : Integer.parseInt(attributeValue);
        StringBuilder stringBuilder = new StringBuilder();
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 4) {
                stringBuilder.append(xmlPullParser.getText());
            }
            if (xmlPullParser.getEventType() == 2) {
                skip(xmlPullParser);
            }
        }
        fontConfig.fileName = SystemFontPath + FILENAME_WHITESPACE_PATTERN.matcher(stringBuilder).replaceAll("");
        return fontConfig;
    }

    private static void skip(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int i = 1;
        while (i > 0) {
            switch (xmlPullParser.next()) {
                case 2:
                    i++;
                    break;
                case 3:
                    i--;
                    break;
                default:
                    break;
            }
        }
    }

    private static FontConfig[] parseJellyBean() throws XmlPullParserException, IOException {
        FontConfig[] fontConfigArr = new FontConfig[0];
        try {
            InputStream fileInputStream = new FileInputStream(SystemFontConfigPath_JellyBean);
            try {
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setInput(fileInputStream, null);
                newPullParser.nextTag();
                fontConfigArr = readFamiliesJellyBean(newPullParser);
                return fontConfigArr;
            } finally {
                fileInputStream.close();
            }
        } catch (IOException e) {
        }

        return fontConfigArr;
    }

    private static FontConfig[] readFamiliesJellyBean(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        xmlPullParser.require(2, null, "familyset");
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("family")) {
                    while (xmlPullParser.next() != 3) {
                        if (xmlPullParser.getEventType() == 2) {
                            if (xmlPullParser.getName().equals("fileset")) {
                                readFileset(xmlPullParser, arrayList);
                            } else {
                                skip(xmlPullParser);
                            }
                        }
                    }
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        FontConfig[] fontConfigArr = new FontConfig[arrayList.size()];
        arrayList.toArray(fontConfigArr);
        return fontConfigArr;
    }

    private static void readFileset(XmlPullParser xmlPullParser, ArrayList<FontConfig> arrayList) throws XmlPullParserException, IOException {
        ArrayList arrayList2 = new ArrayList();
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("file")) {
                    arrayList2.add(readFont(xmlPullParser));
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        if (!arrayList2.isEmpty()) {
            FontConfig fontConfig;
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                fontConfig = (FontConfig) it.next();
                if (fontConfig.weight == 400) {
                    break;
                }
            }
            fontConfig = null;
            if (fontConfig == null) {
                fontConfig = (FontConfig) arrayList2.get(0);
            }
            if (!fontConfig.fileName.isEmpty()) {
                arrayList.add(fontConfig);
            }
        }
    }

    private static FontConfig getFontByLanguage(FontConfig[] fontConfigArr, String str) {
        String toLowerCase = str.toLowerCase();
        for (FontConfig fontConfig : fontConfigArr) {
            if (fontConfig.language.toLowerCase().equals(toLowerCase)) {
                return fontConfig;
            }
        }
        return null;
    }

    private static void addFont(FontConfig fontConfig, ArrayList<String> arrayList, ArrayList<Integer> arrayList2) {
        if (!arrayList.contains(fontConfig.fileName) && new File(fontConfig.fileName).exists()) {
            arrayList.add(fontConfig.fileName);
            arrayList2.add(Integer.valueOf(fontConfig.ttcIndex));
        }
    }

    static void loadSystemFonts() {
        int i = 0;
        if (!systemFontLoaded) {
            int i2;
            systemFontLoaded = true;
            FontConfig[] fontConfigArr = new FontConfig[0];
            if (new File(SystemFontConfigPath_Lollipop).exists()) {
                try {
                    fontConfigArr = parseLollipop();
                } catch (Throwable e) {
                    System.err.println(e.toString());
                }
            } else {
                try {
                    fontConfigArr = parseJellyBean();
                } catch (Throwable e2) {
                    System.err.println(e2.toString());
                }
            }
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            FontConfig fontByLanguage = getFontByLanguage(fontConfigArr, DefaultLanguage);
            if (fontByLanguage != null) {
                addFont(fontByLanguage, arrayList, arrayList2);
            }
            for (String str : FallbackFontFileNames) {
                FontConfig fontConfig = new FontConfig();
                fontConfig.fileName = str;
                addFont(fontConfig, arrayList, arrayList2);
            }
            for (FontConfig addFont : fontConfigArr) {
                addFont(addFont, arrayList, arrayList2);
            }
            if (!arrayList.isEmpty()) {
                String[] strArr = new String[arrayList.size()];
                arrayList.toArray(strArr);
                int[] iArr = new int[arrayList2.size()];
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    i2 = i + 1;
                    iArr[i] = ((Integer) it.next()).intValue();
                    i = i2;
                }
                SetFallbackFontPaths(strArr, iArr);
            }
        }
    }
}
