# 1. ベースイメージを指定
FROM openjdk:17-jdk AS builder

# 2. アプリケーションをコピー
COPY SortService/target /app/SortApplication/

# 3. 作業ディレクトリを設定
WORKDIR /app/SortApplication

ENV HOST 0.0.0.0

EXPOSE 8080

# 4. JARファイルを実行するコマンドを指定
CMD ["java", "-jar", "demo.jar", "--server.port=8080"]
