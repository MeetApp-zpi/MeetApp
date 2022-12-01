const express = require('express');
const app = express();
const http = require('http');
const server = http.createServer(app);
const { Server } = require("socket.io");

const io = new Server(server, {
    path: '/websockets',
    cors: {
        origin: 'http://localhost:5173',
        credentials: true
    }
});

io.use((socket, next) => {
    socket.chatroomId = socket.handshake.auth.chatroomId;

    next();
});

io.on('connection', (socket) => {
    console.log('a user connected');

    socket.join(socket.chatroomId);

    socket.on('priv msg', ({ content, to }) => {
        socket.to(to).emit('priv msg', {
            content,
            from: to
        })
    });
});

server.listen(3000, () => {
    console.log('listening on *:3000');
});