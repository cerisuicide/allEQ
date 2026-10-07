# allEQ JMGO TV Fork

This fork adapts upstream `omixin/allEQ` for the JMGO S901 projector connected
to a XiaoAI Speaker Pro.

## Device contract

- Projector: JMGO S901, Android 11, `armeabi-v7a`
- XiaoAI hardware volume remains around `50` for medium assistant speech.
- Android Bluetooth absolute volume remains disabled.
- Projector media volume can stay at `100`.
- The `Cinematic` preset provides approximately `+7.5 dB` of movie gain with
  the upstream limiter active.

## Package identity

Keep the application id `com.bajintech.assistant`. JMGO middleware
`com.jmgo.hippo` force-stops ordinary third-party background services whenever
the foreground application changes. The S901 firmware's immutable
`CONFIG_BACKGROUND_APP_MANAGER_WHITELIST` contains this unused package id, so
the DSP service survives while video players are foregrounded.

Do not publish this variant to a public app store under that id. It is a
device-specific sideload build.

The maintained sideload signing certificate SHA-256 is
`97821f8dea4b7706d42b067d2e6c54618ac63d106d97f13e3a18e5c103f8262d`.
Future updates must use the same certificate so projector settings and presets
survive upgrades.

## Fork additions

- Starts `AudioProcessorService` after `BOOT_COMPLETED`.
- Adds a Leanback launcher entry for TV launchers.
- Uses a projector-specific version name and code.
- Keeps upstream DSP, limiter, presets, and UI behavior unchanged.

## Required checks

1. `./gradlew test assembleRelease`
2. APK contains only declared offline/audio permissions and supports
   `armeabi-v7a`.
3. Start Ghosten Player and confirm `com.jmgo.hippo` does not stop the DSP
   process.
4. allEQ notification reports one active Bluetooth audio session during movie
   playback.
5. Logcat reports `LoudnessEnhancer` target gain `750 mB` for `Cinematic`.
6. Reboot the projector and confirm the foreground service starts without
   opening the allEQ activity.
