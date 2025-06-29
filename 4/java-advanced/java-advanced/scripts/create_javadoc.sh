MODULE="../../java-advanced-2025/modules/info.kgeorgiy.java.advanced"
MODULES=(
    "${MODULE}.implementor/info/kgeorgiy/java/advanced/implementor/Impler.java"
    "${MODULE}.implementor/info/kgeorgiy/java/advanced/implementor/ImplerException.java"
    "${MODULE}.implementor.tools/info/kgeorgiy/java/advanced/implementor/tools/JarImpler.java"
)
javadoc -notimestamp -private -author -cp .:"${MODULE}" --source-path . -d ../javadoc ../java-solutions/info/kgeorgiy/ja/aksenova/implementor/Implementor.java "${MODULES[@]}"
