'use strict';

const mongoose = require('mongoose');

const roomSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    dimensions: {
        type: String
    },
    location: {
        type: String,
        required: true
    },
    description: {
        type: String,
        required: true
    },
    reservations: [{
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Reservation'
    }]
});

const Room = module.exports = mongoose.model('Room', roomSchema);

