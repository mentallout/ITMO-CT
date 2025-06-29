javac -cp ../../java-advanced-2025/artifacts/info.kgeorgiy.java.advanced.implementor.jar \
      -d . \
      ../java-solutions/info/kgeorgiy/ja/aksenova/implementor/*.java \
      ../../java-advanced-2025/modules/info.kgeorgiy.java.advanced.implementor/info/kgeorgiy/java/advanced/implementor/Impler*.java \
      ../../java-advanced-2025/modules/info.kgeorgiy.java.advanced.implementor.tools/info/kgeorgiy/java/advanced/implementor/tools/JarImpler.java

jar -cfm Implementor.jar META-INF/MANIFEST.MF info

rm -rf info
