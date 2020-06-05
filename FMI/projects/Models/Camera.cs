using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace PhotoStudio.Models
{
    public class Camera
    {
        // string camera_name (attributes (all optional): string shutterspeed; float display; string iso; positiveInteger focus_points; string storage)

        public string camera_image { set; get; }
        public string camera_type { set; get; }
        public string camera_color { set; get; }
        public float camera_megapixels { set; get; } //optional -> BigInteger?

        // optional camera_lens (attributes string lenstype)

        public string camera_manifaturer { set; get; }

        //optional string camera_papertype (attribiutes (all optional): positiveInteger paperlenght; positiveInteger paperwidth)

        //optional string camera_video (attribiutes (all optional): string videotype)
    
        public string camera_processor { set; get; } //optional

        // float camera_price (attribiutes (all optional): string currency)

    }
}