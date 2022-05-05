//
//  LibreLang.swift
//  react-native-libre13
//
//  Created by Macbook on 05.05.2022.
//

import Foundation

public class LibreLang{
    private static var lang = "en"
    private static var supportLangs: Set<String> = ["en", "ru"]
    
    public static func setLang(lang: String){
        let isSupport = self.supportLangs.contains(lang)
        if(isSupport){
            self.lang = lang
        }
    }
    
    static func getLang()->String{
        return self.lang
    }
    
    public static func getActivateTitle()->String{
        switch self.lang{
        case "en": return EnLang.scan_title
        case "ru": return RuLang.scan_title
        default: return "-"
        }
    }
    public static func getScanSuccessTitle()->String{
        switch self.lang{
        case "en": return EnLang.scan_succes
        case "ru": return RuLang.scan_succes
        default: return "-"
        }
    }
}

class RuLang{
    public static var scan_title = "Держите верхнюю часть iPhone рядом с датчиком Libre до второй более продолжительной вибрации."
    public static var scan_succes = "Успешно"
}
class EnLang{
    public static var scan_title = "Hold the top of your iPhone near the Libre sensor until the second longer vibration"
    public static var scan_succes = "Scan Complete"
}


