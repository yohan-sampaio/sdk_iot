#import "SdkSmarthousePlugin.h"
#if __has_include(<sdk_smarthouse/sdk_smarthouse-Swift.h>)
#import <sdk_smarthouse/sdk_smarthouse-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "sdk_smarthouse-Swift.h"
#endif

@implementation SdkSmarthousePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftSdkSmarthousePlugin registerWithRegistrar:registrar];
}
@end
