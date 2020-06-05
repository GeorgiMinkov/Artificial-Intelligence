using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PhotoStudio.Models
{
    public class Tripod
    {
        public string tripod_name { set; get; }
        public string tripod_image { set; get; }
        public float tripod_height { set; get; }
        public string filter_manifacturer { set; get; }

        // float flash_price (attribiutes (all optional): string currency)
    }
}