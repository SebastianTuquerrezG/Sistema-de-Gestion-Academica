# Etapa 1: Build del Frontend
FROM node:20 AS builder

WORKDIR /app

COPY package*.json ./
COPY pnpm-lock.yaml ./
RUN npm install -g pnpm && pnpm install

COPY . .

RUN pnpm run build

# Etapa 2: Servir en Apache
FROM httpd:2.4

# Copiamos el build al directorio raíz de Apache
COPY --from=builder /app/dist/ /usr/local/apache2/htdocs/

# (Opcional) Copiar un archivo httpd.conf personalizado si quieres
# COPY httpd.conf /usr/local/apache2/conf/httpd.conf

EXPOSE 80

CMD ["httpd-foreground"]
