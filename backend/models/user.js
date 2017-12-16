const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    name: {
        type: String,
        unique: true,
        required: true
    },
    email: {
        type: String,
        unique: true,
        required: true
    },
    password: {
        type: String,
        required: true
    },
    role: {
        type: String,
        default: 'user',
        required: true
    },
    logopedist: {
        type: mongoose.Schema.Types.ObjectId
    }

});

const User = module.exports = mongoose.model('User', userSchema);