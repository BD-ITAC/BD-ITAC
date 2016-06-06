var gulp = require('gulp'),
    apidoc = require('gulp-api-doc');

gulp.task('apidoc', () => {
      return gulp.src('server/routes/')
        .pipe(apidoc({markdown: false}))
        .pipe(gulp.dest("public/apidoc"));
});

gulp.task('default',['apidoc']);
