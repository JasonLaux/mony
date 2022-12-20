FROM node:16.18.1 as build
WORKDIR /app
COPY ./frontend/package.json ./frontend/yarn.lock ./
COPY ./frontend/src ./src
COPY ./frontend/public ./public
COPY ./frontend/tsconfig.json ./
RUN yarn install
RUN yarn build

FROM openjdk:20-windowsservercore as app
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ./backend/app.jar
WORKDIR /app/backend
ENTRYPOINT ["java","-jar","app.jar"]