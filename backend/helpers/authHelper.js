exports.loginRequired = function(req, res, next) {
    if (req.user) {
        next();
    } else {
        return res.status(401).json({ message: 'Unauthorized user!' });
    }
};

exports.logoRequired = function(req, res, next) {
    if (req.user && (req.user.data.role === "logo" || req.user.data.role === "admin")) {
        next();
    } else {
        return res.status(401).json({ message: 'Unauthorized logopedist!' });
    }
};

exports.adminRequired = function(req, res, next) {
    if (req.user && req.user.data.role === "admin") {
        next();
    } else {
        return res.status(401).json({ message: 'Unauthorized administrator!' });
    }
};