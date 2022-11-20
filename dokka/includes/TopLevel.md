# Package org.slf4j.kotlin

Function allowing you to instantiate top level loggers

Example:

```kotlin
import org.slf4j.kotlin.toplevel.getLogger


val logger by getLogger()
```

This uses cursed reflection magic (via method handles), so it is slightly slower
than [getLogger][org.slf4j.kotlin.getLogger], however since it should only be used for a top level constant, it will
only be executed once.
