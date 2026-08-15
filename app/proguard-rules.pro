# Add project specific ProGuard rules here.
# Keep the application class
-keep public class com.antifire.owl.AntiFireApp { *; }

# Keep Moshi generated classes
-keepclassmembers,allowobfuscation @androidx.room.Dao
-keep,allowobfuscation @androidx.room.Database
-keep,allowobfuscation @androidx.room.Dao
-keep,allowobfuscation @androidx.room.Entity
-keep,allowobfuscation @androidx.room.PrimaryKey

# Keep model classes
-keep class com.antifire.owl.data.model.** { *; }
-keepclassmembers class com.antifire.owl.data.model.** {
  @com.squareup.moshi.* <fields>;
}

# Keep Retrofit interfaces
-keep,allowobfuscation,allowshrinking @retrofit2.http.*
