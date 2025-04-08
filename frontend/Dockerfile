# Etapa 1: Build del Frontend
FROM node:20 AS builder

WORKDIR /app

COPY package*.json ./
COPY pnpm-lock.yaml ./
RUN npm install -g pnpm && pnpm install

COPY . .

RUN pnpm run build

RUN pnpm run build

# Etapa 2: Servir en Apache
FROM httpd:2.4-bullseye

# Habilitar mod_rewrite "a mano"
RUN sed -i '/LoadModule rewrite_module/s/^#//g' /usr/local/apache2/conf/httpd.conf

# Permitir .htaccess
RUN sed -i '/<Directory "\/usr\/local\/apache2\/htdocs">/,/<\/Directory>/ s/AllowOverride None/AllowOverride All/' /usr/local/apache2/conf/httpd.conf

# Copiar el build y .htaccess
COPY --from=builder /app/dist/ /usr/local/apache2/htdocs/
COPY .htaccess /usr/local/apache2/htdocs/

EXPOSE 80

CMD ["httpd-foreground"]