FROM node:10
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 5000
RUN npm run-script build
RUN npm install -g serve
CMD ["serve","-s","build"]