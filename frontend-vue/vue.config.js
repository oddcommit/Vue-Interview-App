module.exports = {
  css: {
    loaderOptions: {
      sass: {
        prependData: `
            @import "@/scss/global.scss";
            `,
      },
    },
  },
};
