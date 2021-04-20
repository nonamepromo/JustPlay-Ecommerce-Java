var gulp = require('gulp'),
    sass = require('gulp-sass'),
    uglify = require('gulp-uglify'),
    autoprefixer = require('gulp-autoprefixer'),
    concat = require('gulp-concat'),
    twig = require('gulp-twig'),
    prettify = require('gulp-jsbeautifier'),
    changed = require('gulp-changed'),
    rename = require('gulp-rename'),
    browserSync  = require('browser-sync'),
    reload  = browserSync.reload;

gulp.task('sass', function () {
  gulp.src('./scss/*.scss')
    .pipe(sass({outputStyle: 'expanded'}).on('error', sass.logError))
    .pipe(autoprefixer())
    .pipe(gulp.dest('../upload/css'))
    .pipe(sass({outputStyle: 'compressed'}).on('error', sass.logError))
    .pipe(autoprefixer())
    .pipe(rename({suffix: '.min'}))
    .pipe(gulp.dest('../upload/css'))
    .pipe(reload({stream:true}));
});

gulp.task('js', function() {
  gulp.src('./js/theme.js')
    .pipe(gulp.dest('../upload/js/'))
    .pipe(concat('theme.min.js'))
    .pipe(uglify())
    .pipe(gulp.dest('../upload/js/'))
    .pipe(reload({stream:true}));
});

gulp.task('html', function () {
  return gulp.src('./twig/*.twig')
    .pipe(changed('../upload', { extension: '.html' }))
    .pipe(twig())
    .pipe(prettify({
      indent_size: 2,
      extra_liners: ' '
    }))
    .pipe(gulp.dest('../upload'))
    .pipe(reload({stream:true}));
});

gulp.task('build-sass', function () {
  gulp.src('./scss/*.scss')
    .pipe(sass({outputStyle: 'expanded'}).on('error', sass.logError))
    .pipe(autoprefixer())
    .pipe(gulp.dest('../upload/css'))
    .pipe(sass({outputStyle: 'compressed'}).on('error', sass.logError))
    .pipe(autoprefixer())
    .pipe(rename({suffix: '.min'}))
    .pipe(gulp.dest('../upload/css'));
});

gulp.task('build-html', function () {
  return gulp.src('./twig/*.twig')
    .pipe(twig())
    .pipe(prettify({
      indent_size: 2,
      extra_liners: ' '
    }))
    .pipe(gulp.dest('../upload'));
});

gulp.task('browser-sync', function() {
  browserSync.init({
    port: 80,
    notify: false,
    server: {
      baseDir: "../upload/"
    }
  });
});

gulp.task('watch', function () {
  gulp.watch(['./twig/*.twig'], ['html']);
  gulp.watch(['./js/*.js'], ['js']);
  gulp.watch(['./scss/**/*.scss'], ['sass']);
});

gulp.task('default', ['watch', 'browser-sync']);
