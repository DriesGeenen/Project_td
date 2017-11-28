'use strict';

const mongoose = require('mongoose');

const resultSchema = new mongoose.Schema({
    user: {
        type: mongoose.Schema.Types.ObjectId,
        required: true
    },
    exerciseNr: {
        type: Number,
        required: true
    },
    word: {
        type: String,
        required: true
    },
    amountCorrect: {
        type: Number,
        required: true
    },
    amountWrong: {
        type: Number,
        required: true
    },
    date:{
        type: Date,
        required: true
    }
});

const Result = module.exports = mongoose.model('Result', resultSchema);

