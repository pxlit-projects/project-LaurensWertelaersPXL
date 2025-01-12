FROM node:18.19-alpine AS build
WORKDIR /usr/src/app
COPY . .
RUN npm install
RUN npm run build

FROM nginx
COPY nginx.conf /etc/nginx/nginx.conf
# Kopieer de output van de Angular-build naar de standaard nginx directory
COPY --from=build ./dist/newsarticle-frontend/browser /usr/share/nginx/html

EXPOSE 80