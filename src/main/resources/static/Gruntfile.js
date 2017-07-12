module.exports = function(grunt) {
    grunt.initConfig({
        concat: {
            files: {
                // 元ファイルの指定。
                src: 'js/app_controller_*.js',
                // 出力ファイルの名前・パス指定
                dest: 'js/dist/smartbilling.js'
            }
        },
        uglify: {
            dist: {
                files: [{
                    // 出力ファイル: 元ファイル
                    'js/dist/smartbilling.min.js': 'js/dist/smartbilling.js'
                },{
                    // 出力ファイル: 元ファイル
                    'js/dist/vendor.min.js': 'js/vendor.js'
                }]
            }
        },
        cssmin: {
            dist: {
                files: {
                    // 出力ファイル: 元ファイル
                    'css/vendor.min.css': 'css/vendor.css'
                }
            }
        },
        watch: {
            js: {
                files: 'js/*.js', // 監視対象ファイル
                tasks: ['concat', 'uglify'] // 実行させるタスク
            }
        }
    });

    var pkg = grunt.file.readJSON('package.json');
    var taskName;
    for(taskName in pkg.devDependencies) {
        if(taskName.substring(0, 6) == 'grunt-') {
            grunt.loadNpmTasks(taskName);
        }
    }

    grunt.registerTask('watch', ['watch', 'concat', 'uglify', 'cssmin']);
    grunt.registerTask('default', ['concat', 'uglify', 'cssmin']);
};