require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-libre13"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.homepage     = package["homepage"]
  s.license      = package["license"]
  s.authors      = package["author"]

  s.platforms    = { :ios => "15.0" }
  s.source       = { :git => "https://github.com/ddtch/react-native-libre-manager.git", :tag => "#{s.version}" }

  s.source_files = "ios/**" , "ios/RNLibreToolsiOS13/**", "ios/RNLibreToolsiOS13/Foundation/**", "ios/RNLibreToolsiOS13/Foundation/Logger/**", "ios/RNLibreToolsiOS13/LibreToolsResources/**", "ios/RNLibreToolsiOS13/NFCOperations/**"

  s.dependency "React-Core"
end
