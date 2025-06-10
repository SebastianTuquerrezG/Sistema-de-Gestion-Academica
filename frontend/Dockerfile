FROM node:20

WORKDIR /app

# Copiar dependencias
COPY package*.json ./
COPY pnpm-lock.yaml ./
RUN npm install -g pnpm pm2 && pnpm install

# Copiar el resto del código
COPY . .

EXPOSE 3000

# Usar PM2 para ejecutar en modo desarrollo con host 0.0.0.0 y puerto 3000
CMD ["pm2-runtime", "npm", "--name", "frontend-dev", "--", "run", "dev", "--", "--host=0.0.0.0", "--port=3000"]