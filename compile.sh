#!/bin/bash

echo "========================================="
echo "🔨 COMPILANDO SUPER TRUNFO"
echo "========================================="

# Classpath com todos os JARs necessários
CP="lib/javax.persistence.jar:lib/eclipselink-jpa.jar:lib/derby.jar"

echo "Usando classpath:"
echo "$CP"
echo ""

# Limpar compilações anteriores
rm -rf com/

# Compilar
echo "Compilando arquivos Java..."
javac -cp "$CP" -d . src/com/supertrufo/*.java

if [ $? -eq 0 ]; then
    echo "✅ Compilação bem-sucedida!"
    echo ""
    echo "========================================="
    echo "🎮 EXECUTANDO SUPER TRUNFO"
    echo "========================================="
    echo ""
    
    # Executar com classpath incluindo META-INF
    java -cp ".:$CP:META-INF" com.supertrufo.SuperTrunfoGame
else
    echo "❌ Erro na compilação!"
    exit 1
fi
