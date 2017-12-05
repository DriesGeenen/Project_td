const mongoose = require('mongoose');

const userSchema = mongoose.Schema({
    name: {
        type: String
    },
    username: {
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
        default: false,
        required: true
    }

});

const User = module.exports = mongoose.model('User', userSchema);