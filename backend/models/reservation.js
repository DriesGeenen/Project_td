const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');
const config = require('../config/database');

const ObjectId = mongoose.Schema.ObjectId;
const reservationSchema = new mongoose.Schema({

    room: {
        type: ObjectId,
        required: true,
        ref: 'Room'
    },
    user: {
        type: ObjectId,
        required: true,
        ref: 'User'
    },
    starttime: {
        type: Date,
        required: true
    },
    endtime: {
        type: Date,
        required: true
    },
    motivation: {
        type: String,
        required: true
    },
    isConfirmed: {
        type: Boolean,
        default: false,
        required: true
    },
    comment: {
        type: String,
        required: true
    }
});

const Reservation = module.exports = mongoose.model('Reservation', reservationSchema);






