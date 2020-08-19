package course.java.sdm.engine.xml;

import course.java.sdm.engine.SystemManagerSingleton;

/*
3.1.	הקובץ אכן קיים, והוא מסוג XML (די לבדוק לשם כך כי הוא נגמר בסיומת xml)
3.2.	בתוך קבוצת המוצרים, אין 2 מוצרים בעלי id זהה
3.3.	בתוך קבוצת החנויות, אין 2 חנויות בעלי id זהה
3.4.	בתוך חנות אחת, בהגדרת מכירת מוצר – ההפניות הן רק למוצרים קיימים
3.5.	כל מוצר נמכר לפחות ע"י חנות אחת
3.6.	בתוך חנות אחת, מכירת מוצר לא מוגדרת פעמיים
3.7.	מיקומי החנויות לא חורגים מרשת הקורדינטות של 50 ; 50 (x,y: [1,50])
 */
public class FileValidator {
    private String filePath;

    public FileValidator() {
        this.filePath = SystemManagerSingleton.getInstance().getFilePath();
    }


    public boolean validateAppWise() {
        return true;
    }

    public boolean validateExistence() {
        return true;
    }
}
